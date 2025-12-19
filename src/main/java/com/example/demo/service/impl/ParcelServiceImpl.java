public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;

    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public Parcel addParcel(Parcel parcel) {
        if (parcelRepository.existsByTrackingNumber(parcel.getTrackingNumber())) {
            throw new BadRequestException("tracking exists");
        }
        if (parcel.getWeightKg() <= 0) {
            throw new BadRequestException("weight invalid");
        }
        return parcelRepository.save(parcel);
    }

    public Parcel getByTrackingNumber(String trackingNumber) {
        return parcelRepository.findByTrackingNumber(trackingNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Parcel not found"));
    }
}
