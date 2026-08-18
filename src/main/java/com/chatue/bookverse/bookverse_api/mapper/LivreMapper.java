package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.entity.Livre;

@Mapper(componentModel = "spring" , uses= {AuteurMapper.class,CategorieMapper.class})
public interface LivreMapper {

		//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	
	    LivreCompletDto toDto(Livre livre);
		//Méthode qui transforme un DTO en entité -> donc en passe en parametre le dto et l'objet qu'on retourne est l'entité
		Livre toEntity(LivreCompletDto livreResponse);
		
		//Mapper pour la classe LivreResumeDto
		@Mapping(target = "nomAuteur", expression = "java(livre.getAuteur() != null ? livre.getAuteur().getNom() : null)")
		@Mapping(target = "nomCategorie", expression = "java(livre.getCategorie() != null ? livre.getCategorie().getNom() : null)")   
		LivreResumeDto toDtoResume(Livre livre);
		//Methode qui transforme une liste
		List<LivreCompletDto> toDtoListComplet(List<Livre> listLivre);

		@Mapping(target = "nomAuteur", expression = "java(livre.getAuteur() != null ? livre.getAuteur().getNom() : null)")
		@Mapping(target = "nomCategorie", expression = "java(livre.getCategorie() != null ? livre.getCategorie().getNom() : null)")
		List<LivreResumeDto> toDtoListResume(List<Livre> listLivre);
		
		default Livre toEntityLong(Long livreId) {
			if(livreId==null) return null;
			else {
				Livre livre= new Livre();
				livre.setId(livreId);
				return livre;
			}
		}

}
