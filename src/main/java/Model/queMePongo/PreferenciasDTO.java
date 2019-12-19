package Model.queMePongo;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="preferenciasdto")
public class PreferenciasDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	@Column(name="tela")
	private String tela;

	@Column(name="color1")
	private String color1;

	@Column(name="color2")
	private String color2;

	@Column(name="modificador_calor")
	private int modificadorDeCalor = 0;

	@Column(name="calor_cuello")
	private int calorCuello = 0 ;

	@Column(name="calor_manos")
	private int calorManos = 0;

	@Column(name="calor_cabeza")
	private int calorCabeza = 0;
}