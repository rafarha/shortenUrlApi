package com.shortUrl.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by rafael.alves on 20/01/2019.
 */
@Entity
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idUrl;

    @NotBlank(message = "Url Adress is a required Field")
    private String urlAdress;

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public Long getIdUrl() {
	return idUrl;
    }

    public String getUrlAdress() {
	return urlAdress;
    }

    public void setIdUrl(final Long pIdUrl) {
	idUrl = pIdUrl;
    }

    public void setUrlAdress(final String pUrlAdress) {
	urlAdress = pUrlAdress;
    }

}
