package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

public class SensorComponent extends AbstractComponent {

	private TransceiverComponent relatedTransceiver;
	
	public SensorComponent(TransceiverComponent relatedTransceiver) {
		this.relatedTransceiver = relatedTransceiver;
	}
}
