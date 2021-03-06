package org.matsim.contrib.transEnergySim.chargingInfrastructure.stationary;

import org.matsim.api.core.v01.Identifiable;
import org.matsim.contrib.transEnergySim.agents.VehicleAgent;
import org.matsim.contrib.transEnergySim.events.ChargingEvent;
import org.matsim.contrib.transEnergySim.vehicles.api.VehicleWithBattery;

public interface ChargingPlug extends Identifiable<ChargingPlug> {

	ChargingPoint getChargingPoint();
	
	ChargingPlugStatus getChargingPlugStatus();
	ChargingPlugType getChargingPlugType();
	
	void plugVehicle(VehicleWithBattery vehicle);
	void unPlugVehicle(VehicleWithBattery vehicle);

	VehicleWithBattery getVehicle();

	double getMaxChargingPowerInWatt();
	double getActualChargingPowerInWatt();

	double getEnergyDeliveredByTime(double time);

	ChargingSite getChargingSite();

	double estimateChargingSessionDuration();
	
	void handleBeginChargeEvent();

	void handleEndChargingSession();

	void registerPlugInaccessible();

	void registerPlugAccessible();

	void handleBeginChargingSession(VehicleAgent agent);
}
