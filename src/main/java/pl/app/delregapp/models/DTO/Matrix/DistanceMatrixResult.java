package pl.app.delregapp.models.DTO.Matrix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DistanceMatrixResult {
    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;

    @JsonProperty("origin_addresses")
    private List<String> originAddresses;

    @JsonProperty("rows")
    private List<Row> rows;

    @JsonProperty("status")
    private String status;
}
