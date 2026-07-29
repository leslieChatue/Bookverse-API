package com.chatue.bookverse.bookverse_api.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.chatue.bookverse.bookverse_api.dto.LignePanierDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PanierRequest {
	
	@NotBlank(message="Merci de bien vouloir saisir une valeur!")
	private Long userId;
	
    private List<LignePanierDTO> lignes;
    @UpdateTimestamp
    private LocalDateTime dateCreation;
}
