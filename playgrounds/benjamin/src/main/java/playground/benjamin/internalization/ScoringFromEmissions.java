/* *********************************************************************** *
 * project: org.matsim.*
 * ScoringFromEmissions.java
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
package playground.benjamin.internalization;

import org.apache.log4j.Logger;
import org.matsim.core.scoring.CharyparNagelScoringParameters;
import org.matsim.core.scoring.interfaces.BasicScoring;

import playground.benjamin.emissions.events.ColdEmissionEvent;
import playground.benjamin.emissions.events.ColdEmissionEventHandler;
import playground.benjamin.emissions.events.WarmEmissionEvent;
import playground.benjamin.emissions.events.WarmEmissionEventHandler;

/**
 * @author benjamin
 *
 */
public class ScoringFromEmissions implements BasicScoring, WarmEmissionEventHandler, ColdEmissionEventHandler{
	
	private static final Logger logger = Logger.getLogger(ScoringFromEmissions.class);

	CharyparNagelScoringParameters params;

	public ScoringFromEmissions(CharyparNagelScoringParameters params) {
		this.params = params;
		logger.info("using " + ScoringFromEmissions.class.getName() + "...");
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
	}

	@Override
	public double getScore() {
		Double marginalUtlOfMoney = this.params.marginalUtilityOfMoney;
		
		return 0;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(WarmEmissionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(int iteration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(ColdEmissionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
