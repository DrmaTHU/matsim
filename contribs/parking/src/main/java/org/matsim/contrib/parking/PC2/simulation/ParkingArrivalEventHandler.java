package org.matsim.contrib.parking.PC2.simulation;

import org.matsim.core.events.handler.EventHandler;

public interface ParkingArrivalEventHandler extends EventHandler {
	public void handleEvent (ParkingArrivalEvent event);
}

