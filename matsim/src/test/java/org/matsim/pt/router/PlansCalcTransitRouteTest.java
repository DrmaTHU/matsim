/* *********************************************************************** *
 * project: org.matsim.*
 * PlansCalcTransitRouteTest.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
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

package org.matsim.pt.router;

import java.util.List;

import junit.framework.TestCase;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.PlansCalcRouteConfigGroup;
import org.matsim.core.config.groups.VspExperimentalConfigGroup;
import org.matsim.core.population.ActivityImpl;
import org.matsim.core.population.PopulationFactoryImpl;
import org.matsim.core.router.costcalculators.FreespeedTravelTimeAndDisutility;
import org.matsim.core.router.util.DijkstraFactory;
import org.matsim.pt.config.TransitConfigGroup;
import org.matsim.pt.config.TransitRouterConfigGroup;

public class PlansCalcTransitRouteTest extends TestCase {

	public void testTransitActivities() {
		Fixture f = new Fixture();
		f.init();

		TransitConfigGroup transitConfig = new TransitConfigGroup();
		PlansCalcRouteConfigGroup routerConfig = new PlansCalcRouteConfigGroup();
		PlanCalcScoreConfigGroup scoreConfig = new PlanCalcScoreConfigGroup();
		TransitRouterConfigGroup transitRouterConfigGroup = new TransitRouterConfigGroup();
		VspExperimentalConfigGroup vspConfigGroup = new VspExperimentalConfigGroup() ;

		FreespeedTravelTimeAndDisutility ttc = new FreespeedTravelTimeAndDisutility(new PlanCalcScoreConfigGroup());
		TransitRouterConfig transitRouterConfig = new TransitRouterConfig(scoreConfig, routerConfig, transitRouterConfigGroup,
				vspConfigGroup);
		
		PlansCalcTransitRoute router = new PlansCalcTransitRoute(routerConfig, f.network, ttc, ttc, new DijkstraFactory(), 
				((PopulationFactoryImpl) f.scenario.getPopulation().getFactory()).getModeRouteFactory(), transitConfig, new TransitRouterImpl(transitRouterConfig, f.schedule ), f.schedule);

		Coord fromCoord = f.scenario.createCoord(3800, 5100);
		Coord toCoord = f.scenario.createCoord(16100, 10050);

		PopulationFactory pb = f.scenario.getPopulation().getFactory();
		Plan plan = pb.createPlan();
		Activity act1 = pb.createActivityFromCoord("h", fromCoord);
		((ActivityImpl) act1).setLinkId(f.scenario.createId("1"));
		act1.setEndTime(5.0*3600);
		Leg leg = pb.createLeg(TransportMode.pt);
		Activity act2 = pb.createActivityFromCoord("w", toCoord);
		((ActivityImpl) act2).setLinkId(f.scenario.createId("2"));
		plan.addActivity(act1);
		plan.addLeg(leg);
		plan.addActivity(act2);

		router.run(plan);

		List<PlanElement> pes = plan.getPlanElements();
		assertEquals(11, pes.size());
		assertEquals(TransportMode.transit_walk, ((Leg) pes.get(1)).getMode());
		assertEquals(f.schedule.getFacilities().get(f.scenario.createId("0")).getLinkId(), ((Activity) pes.get(2)).getLinkId());
		assertEquals(TransportMode.pt, ((Leg) pes.get(3)).getMode());
		assertEquals(f.schedule.getFacilities().get(f.scenario.createId("4")).getLinkId(), ((Activity) pes.get(4)).getLinkId());
		assertEquals(TransportMode.transit_walk, ((Leg) pes.get(5)).getMode());
		assertEquals(f.schedule.getFacilities().get(f.scenario.createId("18")).getLinkId(), ((Activity) pes.get(6)).getLinkId());
		assertEquals(TransportMode.pt, ((Leg) pes.get(7)).getMode());
		assertEquals(f.schedule.getFacilities().get(f.scenario.createId("19")).getLinkId(), ((Activity) pes.get(8)).getLinkId());
		assertEquals(TransportMode.transit_walk, ((Leg) pes.get(9)).getMode());
	}
}