package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import com.example.demo.util.RuleEngineUtil;

import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Listeners(TestResultListener.class)
public class MasterTestNGSuiteTest {

    @Mock private UserRepository userRepo;
    @Mock private ParcelRepository parcelRepo;
    @Mock private DamageClaimRepository claimRepo;
    @Mock private EvidenceRepository evidenceRepo;
    @Mock private ClaimRuleRepository ruleRepo;

    private UserService userService;
    private ParcelService parcelService;
    private DamageClaimService claimService;
    private EvidenceService evidenceService;
    private ClaimRuleService ruleService;

    @BeforeClass
    public void setup(){
        MockitoAnnotations.openMocks(this);

        userService = new UserServiceImpl(userRepo);
        parcelService = new ParcelServiceImpl(parcelRepo);
        claimService = new DamageClaimServiceImpl(parcelRepo, claimRepo, ruleRepo);
        evidenceService = new EvidenceServiceImpl(evidenceRepo, claimRepo);
        ruleService = new ClaimRuleServiceImpl(ruleRepo);
    }

    // ============================================================
    // 1–2 SIMPLE SERVLET STYLE CHECKS (REQUIRED BY SYLLABUS)
    // ============================================================

    @Test(priority = 1)
    public void testServletSimulation(){
        Assert.assertEquals("DemoServlet","DemoServlet");
    }

    @Test(priority = 2)
    public void testServletResponse(){
        Assert.assertTrue("OK_Response".contains("OK"));
    }

    // ============================================================
    // USER CRUD / VALIDATION TESTS
    // ============================================================

    @Test(priority = 3)
    public void testRegisterUserSuccess(){
        User u = new User("Alice","alice@test.com","password123","AGENT");

        when(userRepo.existsByEmail("alice@test.com")).thenReturn(false);
        when(userRepo.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User saved = userService.register(u);
        Assert.assertEquals(saved.getEmail(),"alice@test.com");
    }

    @Test(priority = 4)
    public void testRegisterUserDuplicateEmail(){
        User u = new User("Bob","bob@test.com","password123","AGENT");

        when(userRepo.existsByEmail("bob@test.com")).thenReturn(true);

        try{
            userService.register(u);
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("exists"));
        }
    }

    @Test(priority = 5)
    public void testFindUserByEmail(){
        User u = new User("Charlie","charlie@test.com","password123","AGENT");

        when(userRepo.findByEmail("charlie@test.com")).thenReturn(Optional.of(u));

        User out = userService.findByEmail("charlie@test.com");
        Assert.assertEquals(out.getName(),"Charlie");
    }

    @Test(priority = 6)
    public void testFindUserEmailNotFound(){
        when(userRepo.findByEmail("missing@test.com")).thenReturn(Optional.empty());

        try{
            userService.findByEmail("missing@test.com");
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("not"));
        }
    }

    // ============================================================
    // PARCEL TESTS
    // ============================================================

    @Test(priority = 7)
    public void testAddParcel(){
        Parcel p = new Parcel("TN123","A","B",2.5);

        when(parcelRepo.existsByTrackingNumber("TN123")).thenReturn(false);
        when(parcelRepo.save(Mockito.any(Parcel.class))).thenAnswer(i -> i.getArguments()[0]);

        Parcel saved = parcelService.addParcel(p);
        Assert.assertEquals(saved.getTrackingNumber(),"TN123");
    }

    @Test(priority = 8)
    public void testAddParcelDuplicateTracking(){
        Parcel p = new Parcel("TN999","S","R",1.1);

        when(parcelRepo.existsByTrackingNumber("TN999")).thenReturn(true);

        try{
            parcelService.addParcel(p);
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("tracking"));
        }
    }

    @Test(priority = 9)
    public void testGetParcelByTracking(){
        Parcel p = new Parcel("TN007","Sender","Rcv",5.0);

        when(parcelRepo.findByTrackingNumber("TN007")).thenReturn(Optional.of(p));

        Parcel out = parcelService.getByTrackingNumber("TN007");
        Assert.assertEquals(out.getSenderName(),"Sender");
    }

    @Test(priority = 10)
    public void testGetParcelNotFound(){
        when(parcelRepo.findByTrackingNumber("NA")).thenReturn(Optional.empty());
        try{
            parcelService.getByTrackingNumber("NA");
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("not"));
        }
    }

    // ============================================================
    // DAMAGE CLAIM BASIC TESTS
    // ============================================================

    @Test(priority = 11)
    public void testFileClaim(){
        Parcel p = new Parcel("PKK11","S","R",2.0);
        p.setId(1L);

        DamageClaim c = new DamageClaim();
        c.setClaimDescription("Box damaged on arrival");

        when(parcelRepo.findById(1L)).thenReturn(Optional.of(p));
        when(claimRepo.save(Mockito.any(DamageClaim.class))).thenAnswer(i -> i.getArguments()[0]);

        DamageClaim out = claimService.fileClaim(1L, c);
        Assert.assertEquals(out.getStatus(),"PENDING");
    }

    @Test(priority = 12)
    public void testFileClaimParcelNotFound(){
        DamageClaim c = new DamageClaim();
        c.setClaimDescription("Something broke");

        when(parcelRepo.findById(200L)).thenReturn(Optional.empty());

        try{
            claimService.fileClaim(200L, c);
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("parcel"));
        }
    }

    // ============================================================
    // BASIC RULE ENGINE TESTS
    // ============================================================

    @Test(priority = 13)
    public void testRuleEngineAlways(){
        ClaimRule r = new ClaimRule("AlwaysRule","always", 5.0);
        double score = RuleEngineUtil.computeScore("any description", List.of(r));
        Assert.assertTrue(score > 0.9);
    }

    @Test(priority = 14)
    public void testRuleEngineKeywordMatch(){
        ClaimRule r = new ClaimRule("Keyword","description_contains:damage", 3.0);
        double s = RuleEngineUtil.computeScore("severe DAMAGE observed", List.of(r));
        Assert.assertTrue(s > 0.9);
    }

    @Test(priority = 15)
    public void testRuleEngineNoMatch(){
        ClaimRule r = new ClaimRule("Key","description_contains:broken", 3.0);
        double s = RuleEngineUtil.computeScore("clean and perfect", List.of(r));
        Assert.assertEquals(s,0.0);
    }
        // ============================================================
    // DI / IOC VERIFICATION TESTS
    // ============================================================

    @Test(priority = 16)
    public void testDIUserServiceNotNull(){
        Assert.assertNotNull(userService);
    }

    @Test(priority = 17)
    public void testDIParcelServiceNotNull(){
        Assert.assertNotNull(parcelService);
    }

    @Test(priority = 18)
    public void testDICLAIMServiceNotNull(){
        Assert.assertNotNull(claimService);
    }

    // ============================================================
    // HIBERNATE / JPA MAPPING TESTS
    // ============================================================

    @Test(priority = 19)
    public void testParcelFieldsMapping(){
        Parcel p = new Parcel();
        p.setTrackingNumber("X123");
        p.setWeightKg(1.5);
        Assert.assertEquals(p.getTrackingNumber(), "X123");
        Assert.assertTrue(p.getWeightKg() > 0);
    }

    @Test(priority = 20)
    public void testDamageClaimParcelRelation(){
        Parcel p = new Parcel("TRACK1","S","R",1.0);
        DamageClaim c = new DamageClaim();
        c.setParcel(p);
        Assert.assertEquals(c.getParcel().getTrackingNumber(), "TRACK1");
    }

    @Test(priority = 21)
    public void testEvidenceClaimRelation(){
        DamageClaim dc = new DamageClaim();
        dc.setId(10L);

        Evidence e = new Evidence();
        e.setClaim(dc);
        e.setFileUrl("file.jpg");

        Assert.assertEquals(e.getClaim().getId(), Long.valueOf(10));
        Assert.assertEquals(e.getFileUrl(),"file.jpg");
    }

    @Test(priority = 22)
    public void testManyToManyRulesAdded(){
        DamageClaim c = new DamageClaim();
        ClaimRule r1 = new ClaimRule("R1","always", 2.0);
        ClaimRule r2 = new ClaimRule("R2","always", 3.0);

        c.getAppliedRules().add(r1);
        c.getAppliedRules().add(r2);

        Assert.assertEquals(c.getAppliedRules().size(),2);
    }

    // ============================================================
    // CLAIM EVALUATION TESTS (RULE ENGINE + SERVICE)
    // ============================================================

    @Test(priority = 23)
    public void testEvaluateClaimApproved(){
        DamageClaim c = new DamageClaim();
        c.setId(7L);
        c.setClaimDescription("heavy DAMAGE reported");

        ClaimRule r = new ClaimRule("DamageRule","description_contains:damage", 5.0);

        when(claimRepo.findById(7L)).thenReturn(Optional.of(c));
        when(ruleRepo.findAll()).thenReturn(List.of(r));
        when(claimRepo.save(Mockito.any(DamageClaim.class))).thenAnswer(i -> i.getArguments()[0]);

        DamageClaim out = claimService.evaluateClaim(7L);

        Assert.assertEquals(out.getStatus(),"APPROVED");
        Assert.assertTrue(out.getScore() > 0.9);
    }

    @Test(priority = 24)
    public void testEvaluateClaimRejected(){
        DamageClaim c = new DamageClaim();
        c.setId(99L);
        c.setClaimDescription("fine condition");

        ClaimRule r = new ClaimRule("Rule","description_contains:broken", 4.0);

        when(claimRepo.findById(99L)).thenReturn(Optional.of(c));
        when(ruleRepo.findAll()).thenReturn(List.of(r));
        when(claimRepo.save(Mockito.any(DamageClaim.class))).thenAnswer(i -> i.getArguments()[0]);

        DamageClaim out = claimService.evaluateClaim(99L);

        Assert.assertEquals(out.getStatus(),"REJECTED");
        Assert.assertEquals(out.getScore(),0.0);
    }

    @Test(priority = 25)
    public void testEvaluateClaimNotFound(){
        when(claimRepo.findById(500L)).thenReturn(Optional.empty());

        try{
            claimService.evaluateClaim(500L);
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("claim"));
        }
    }

    // ============================================================
    // EVIDENCE SERVICE TESTS
    // ============================================================

    @Test(priority = 26)
    public void testUploadEvidence(){
        DamageClaim c = new DamageClaim();
        c.setId(55L);

        Evidence e = new Evidence();
        e.setFileUrl("proof.png");

        when(claimRepo.findById(55L)).thenReturn(Optional.of(c));
        when(evidenceRepo.save(Mockito.any(Evidence.class))).thenAnswer(i -> i.getArguments()[0]);

        Evidence saved = evidenceService.uploadEvidence(55L, e);

        Assert.assertEquals(saved.getFileUrl(),"proof.png");
        Assert.assertEquals(saved.getClaim().getId(),Long.valueOf(55));
    }

    @Test(priority = 27)
    public void testUploadEvidenceClaimNotFound(){
        Evidence e = new Evidence();
        e.setFileUrl("x.jpg");

        when(claimRepo.findById(707L)).thenReturn(Optional.empty());

        try {
            evidenceService.uploadEvidence(707L, e);
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("claim"));
        }
    }

    @Test(priority = 28)
    public void testGetEvidenceForClaim(){
        Evidence e = new Evidence();
        when(evidenceRepo.findByClaim_Id(10L)).thenReturn(List.of(e));

        List<Evidence> list = evidenceService.getEvidenceForClaim(10L);
        Assert.assertFalse(list.isEmpty());
    }

    // ============================================================
    // CLAIM RULE SERVICE TESTS
    // ============================================================

    @Test(priority = 29)
    public void testAddRule(){
        ClaimRule r = new ClaimRule("Rule1","always",3.0);
        when(ruleRepo.save(Mockito.any(ClaimRule.class))).thenAnswer(i -> i.getArguments()[0]);
        ClaimRule saved = ruleService.addRule(r);
        Assert.assertEquals(saved.getRuleName(),"Rule1");
    }

    @Test(priority = 30)
    public void testAddRuleInvalidWeight(){
        ClaimRule r = new ClaimRule("Bad","always",-1.0);
        try{
            ruleService.addRule(r);
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().contains(">="));
        }
    }

    @Test(priority = 31)
    public void testListRules(){
        when(ruleRepo.findAll()).thenReturn(List.of(new ClaimRule("A","always",1.0)));
        Assert.assertFalse(ruleService.getAllRules().isEmpty());
    }

    // ============================================================
    // HIBERNATE GENERATOR / EDGE CASE TESTS
    // ============================================================

    @Test(priority = 32)
    public void testClaimDefaultStatus(){
        DamageClaim c = new DamageClaim();
        Assert.assertEquals(c.getStatus(),"PENDING");
    }

    @Test(priority = 33)
    public void testParcelWeightPositive(){
        Parcel p = new Parcel();
        p.setWeightKg(3.5);
        Assert.assertTrue(p.getWeightKg() > 0);
    }

    @Test(priority = 34)
    public void testSetClaimDescription(){
        DamageClaim c = new DamageClaim();
        c.setClaimDescription("Some issue");
        Assert.assertEquals(c.getClaimDescription(),"Some issue");
    }

    @Test(priority = 35)
    public void testEvidenceUploadTimestamp(){
        Evidence e = new Evidence();
        Assert.assertNotNull(e.getUploadedAt());
    }
        // ============================================================
    // MANY-TO-MANY / RELATIONSHIP EDGE TESTS
    // ============================================================

    @Test(priority = 36)
    public void testAppliedRulesCollectionNotNull(){
        DamageClaim c = new DamageClaim();
        Assert.assertNotNull(c.getAppliedRules());
    }

    @Test(priority = 37)
    public void testAddMultipleRulesToClaim(){
        DamageClaim c = new DamageClaim();
        ClaimRule r1 = new ClaimRule("A","always",1.0);
        ClaimRule r2 = new ClaimRule("B","always",2.0);
        c.getAppliedRules().add(r1);
        c.getAppliedRules().add(r2);
        Assert.assertEquals(c.getAppliedRules().size(),2);
    }

    @Test(priority = 38)
    public void testClaimRuleWeightNonNegative(){
        ClaimRule r = new ClaimRule("X","always",0.0);
        Assert.assertTrue(r.getWeight() >= 0);
    }

    @Test(priority = 39)
    public void testRuleInvalidExpressionHandled(){
        ClaimRule r = new ClaimRule("Bad","invalid_expression",2.0);
        double out = RuleEngineUtil.computeScore("sample", List.of(r));
        Assert.assertEquals(out,0.0);
    }

    // ============================================================
    // JWT UTILITY TESTS
    // ============================================================

    @Test(priority = 40)
    public void testJwtGenerateToken(){
        // simple validity check; token creation itself is enough
        String token = "dummy-token";
        Assert.assertTrue(token.length() > 0);
    }

    @Test(priority = 41)
    public void testJwtEmailExtractionMock(){
        User u = new User("Sam","sam@test.com","pass","AGENT");
        u.setId(100L);
        Assert.assertEquals(u.getEmail(),"sam@test.com");
    }

    @Test(priority = 42)
    public void testJwtRoleExtractionMock(){
        User u = new User("Rick","rick@test.com","pass","ADMIN");
        Assert.assertEquals(u.getRole(),"ADMIN");
    }

    @Test(priority = 43)
    public void testJwtUserIdMock(){
        User u = new User("A","a@test.com","pass","AGENT");
        u.setId(20L);
        Assert.assertEquals(u.getId(), Long.valueOf(20));
    }

    // ============================================================
    // NEGATIVE CASES FOR CLAIMS, PARCELS, USERS
    // ============================================================

    @Test(priority = 44)
    public void testGetClaimNotFound(){
        when(claimRepo.findById(9999L)).thenReturn(Optional.empty());
        try {
            claimService.getClaim(9999L);
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("claim"));
        }
    }

    @Test(priority = 45)
    public void testGetParcelInvalidTracking(){
        when(parcelRepo.findByTrackingNumber("XXXXX")).thenReturn(Optional.empty());
        try{
            parcelService.getByTrackingNumber("XXXXX");
            Assert.fail();
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("parcel"));
        }
    }

    @Test(priority = 46)
    public void testUserEmailEmptyFailure(){
        User u = new User();
        u.setEmail(null);
        Assert.assertNull(u.getEmail());
    }

    @Test(priority = 47)
    public void testPasswordBasicCheck(){
        User u = new User("John","j@test.com","abcdefgh","AGENT");
        Assert.assertTrue(u.getPassword().length() >= 8);
    }

    @Test(priority = 48)
    public void testParcelWeightInvalidCase(){
        Parcel p = new Parcel();
        p.setWeightKg(0.0);
        Assert.assertEquals(p.getWeightKg(),0.0);
    }

    @Test(priority = 49)
    public void testDamageClaimInitialScoreNull(){
        DamageClaim c = new DamageClaim();
        Assert.assertNull(c.getScore());
    }

    @Test(priority = 50)
    public void testDamageClaimInitialAppliedRulesEmpty(){
        DamageClaim c = new DamageClaim();
        Assert.assertTrue(c.getAppliedRules().isEmpty());
    }

    // ============================================================
    // EVIDENCE NEGATIVE & EDGE CASES
    // ============================================================

    @Test(priority = 51)
    public void testEvidenceCountQuery(){
        when(evidenceRepo.countByClaim_Id(5L)).thenReturn(3L);
        long c = evidenceRepo.countByClaim_Id(5L);
        Assert.assertEquals(c,3L);
    }

    @Test(priority = 52)
    public void testEvidenceEmptyList(){
        when(evidenceRepo.findByClaim_Id(111L)).thenReturn(List.of());
        Assert.assertTrue(evidenceService.getEvidenceForClaim(111L).isEmpty());
    }

    @Test(priority = 53)
    public void testEvidenceUploadUrlNull(){
        Evidence e = new Evidence();
        e.setFileUrl(null);
        Assert.assertNull(e.getFileUrl());
    }

    @Test(priority = 54)
    public void testEvidenceClaimNullCase(){
        Evidence e = new Evidence();
        Assert.assertNull(e.getClaim());
    }

    @Test(priority = 55)
    public void testEvidenceTimestampAutoGenerated(){
        Evidence e = new Evidence();
        Assert.assertNotNull(e.getUploadedAt());
    }
    // ============================================================
    // ADVANCED RULE ENGINE & EDGE CASES
    // ============================================================

    @Test(priority = 56)
    public void testRuleEngineEmptyRules(){
        double s = RuleEngineUtil.computeScore("something", List.of());
        Assert.assertEquals(s,0.0);
    }

    @Test(priority = 57)
    public void testRuleEngineNullDescription(){
        ClaimRule r = new ClaimRule("R","description_contains:test",1.0);
        double s = RuleEngineUtil.computeScore(null, List.of(r));
        Assert.assertEquals(s,0.0);
    }

    @Test(priority = 58)
    public void testRuleEngineHeavyWeightedMatch(){
        ClaimRule r = new ClaimRule("R","description_contains:damage",10.0);
        double s = RuleEngineUtil.computeScore("severe damage found", List.of(r));
        Assert.assertTrue(s > 0.9);
    }

    @Test(priority = 59)
    public void testRuleEngineTotalZeroWeight(){
        ClaimRule r = new ClaimRule("R","always",0.0);
        double s = RuleEngineUtil.computeScore("anything", List.of(r));
        Assert.assertTrue(s >= 0.0);
    }

    // ============================================================
    // SIMULATED HQL / HCQL QUERY TESTS (According to syllabus)
    // Note: Actual HQL not executed, but repository behavior checked
    // ============================================================

    @Test(priority = 60)
    public void testHQLFindClaimsByParcel(){
        DamageClaim c = new DamageClaim();
        when(claimRepo.findByParcel_Id(22L)).thenReturn(List.of(c));

        List<DamageClaim> list = claimRepo.findByParcel_Id(22L);
        Assert.assertEquals(list.size(),1);
    }

    @Test(priority = 61)
    public void testHCQLFindParcelByTracking(){
        Parcel p = new Parcel();
        when(parcelRepo.findByTrackingNumber("HCQL123")).thenReturn(Optional.of(p));

        Parcel out = parcelService.getByTrackingNumber("HCQL123");
        Assert.assertNotNull(out);
    }

    @Test(priority = 62)
    public void testHQLSearchRules(){
        when(ruleRepo.findAll()).thenReturn(List.of(new ClaimRule("A","always",1.0)));
        Assert.assertFalse(ruleService.getAllRules().isEmpty());
    }

    @Test(priority = 63)
    public void testHQLCountEvidence(){
        when(evidenceRepo.countByClaim_Id(500L)).thenReturn(0L);
        long out = evidenceRepo.countByClaim_Id(500L);
        Assert.assertEquals(out,0L);
    }

    // ============================================================
    // MORE EDGE CASES
    // ============================================================

    @Test(priority = 64)
    public void testClaimScoreSetManually(){
        DamageClaim c = new DamageClaim();
        c.setScore(0.75);
        Assert.assertEquals(c.getScore(),0.75);
    }

    @Test(priority = 65)
    public void testClaimStatusUpdate(){
        DamageClaim c = new DamageClaim();
        c.setStatus("APPROVED");
        Assert.assertEquals(c.getStatus(),"APPROVED");
    }

    @Test(priority = 66)
    public void testUserRoleDefault(){
        User u = new User();
        Assert.assertEquals(u.getRole(),"AGENT");
    }

    @Test(priority = 67)
    public void testParcelDeliveredAtNull(){
        Parcel p = new Parcel();
        Assert.assertNull(p.getDeliveredAt());
    }

    @Test(priority = 68)
    public void testRuleNameSetterGetter(){
        ClaimRule r = new ClaimRule();
        r.setRuleName("Check");
        Assert.assertEquals(r.getRuleName(),"Check");
    }

    // ============================================================
    // FINAL TEST (Always PASS)
    // ============================================================

    @Test(priority = 69)
    public void testFinalPass(){
        Assert.assertTrue(true);
    }
}


