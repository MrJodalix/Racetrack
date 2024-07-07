package logic;

/**
 * An Error enum for validating the track
 *
 * @author Joshua-Scott Schöttke
 */
public enum ValidTrack {
    OK("Track is valid."),
    TO_SHORT("Track is too short."),
    TO_LOW("Track is too low."),
    TO_LONG("Track is too long."),
    TO_HIGH("Track is too high."),
    SECOND_LINE("There is a second line."),
    LINE_NOT_VALID("The line is not valid."),
    NOT_A_CIRCLE("Street is not a circle."),
    TRACK_NOT_QUADRATIC ("Track is not quadratic.");

    private final String status;

    /**
     * Construct a Error Code of the Track
     * @param status the Status of the Errorcode
     */
    ValidTrack (String status) {this.status = status;}

    public String getStatus() {
        return status;
    }
}
