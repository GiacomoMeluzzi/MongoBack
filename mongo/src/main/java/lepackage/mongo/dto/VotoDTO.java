package lepackage.mongo.dto;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VotoDTO {

	@Id
	private String _id;
	private String materiaId;
	private String studenteId;
	private Integer voto;
	
}
