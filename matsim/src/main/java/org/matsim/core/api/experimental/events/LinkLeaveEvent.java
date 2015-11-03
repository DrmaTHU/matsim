/* *********************************************************************** *
 * project: org.matsim.*
 * LinkLeaveEvent.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007, 2008 by the members listed in the COPYING,  *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.core.api.experimental.events;

import java.util.Map;

import org.matsim.api.core.v01.Id;

/**
 * Design considerations: <ul>
 * <li> This class deliberately does <i>not</i> implement HasPersonId.  One reason is that it does not really
 * belong at this level (since it is the vehicle that enters/leaves links); another reason is that this would
 * make an "instanceof HasPersonId" considerably more expensive. kai/dg, dec'12
 * </ul> 
 *
 */
public class LinkLeaveEvent extends Event {

	public static final String EVENT_TYPE = "left link";
	public static final String ATTRIBUTE_VEHICLE = "vehicle";
	private final Id vehicleId;

	public LinkLeaveEvent(final double time, final Id agentId, final Id linkId, Id vehicleId) {
		super(time);
		this.personId = agentId;
		this.linkId = linkId;
		this.vehicleId = vehicleId;
	}

	@Override
	public String getEventType() {
		return EVENT_TYPE;
	}

	public Id getVehicleId() {
		return vehicleId;
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String, String> attr = super.getAttributes();
		attr.put(ATTRIBUTE_PERSON, this.personId.toString());
		attr.put(ATTRIBUTE_LINK, this.linkId.toString());
		if (this.vehicleId != null) {
			attr.put(ATTRIBUTE_VEHICLE, this.vehicleId.toString());
		}
		return attr;
	}
	

	public static final String ATTRIBUTE_LINK = "link";

	private final Id linkId;

	public Id getLinkId() {
		return this.linkId;
	}

	public static final String ATTRIBUTE_PERSON = "person";

	private final Id personId;

	public Id getPersonId() {
		return this.personId;
	}
	
}