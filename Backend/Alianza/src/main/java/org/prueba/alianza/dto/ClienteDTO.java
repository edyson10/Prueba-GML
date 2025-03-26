package org.prueba.alianza.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class ClienteDTO {

    String sharedKey;
    String businessId;
    String email;
    String phone;
    Date dateAdded;
    Date startDate;
    Date endDate;

}
