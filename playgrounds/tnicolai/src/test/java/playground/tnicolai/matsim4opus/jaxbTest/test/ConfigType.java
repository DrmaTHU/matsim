//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.30 at 04:23:23 PM CEST 
//


package playground.tnicolai.matsim4opus.jaxbTest.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for configType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="configType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="network" type="{}networkType"/>
 *         &lt;element name="inputPlansFile" type="{}inputPlansFileType" minOccurs="0"/>
 *         &lt;element name="controler" type="{}controlerType"/>
 *         &lt;element name="planCalcScore" type="{}planCalcScoreType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configType", propOrder = {
    "network",
    "inputPlansFile",
    "controler",
    "planCalcScore"
})
public class ConfigType {

    @XmlElement(required = true)
    protected NetworkType network;
    protected InputPlansFileType inputPlansFile;
    @XmlElement(required = true)
    protected ControlerType controler;
    @XmlElement(required = true)
    protected PlanCalcScoreType planCalcScore;

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkType }
     *     
     */
    public NetworkType getNetwork() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkType }
     *     
     */
    public void setNetwork(NetworkType value) {
        this.network = value;
    }

    /**
     * Gets the value of the inputPlansFile property.
     * 
     * @return
     *     possible object is
     *     {@link InputPlansFileType }
     *     
     */
    public InputPlansFileType getInputPlansFile() {
        return inputPlansFile;
    }

    /**
     * Sets the value of the inputPlansFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputPlansFileType }
     *     
     */
    public void setInputPlansFile(InputPlansFileType value) {
        this.inputPlansFile = value;
    }

    /**
     * Gets the value of the controler property.
     * 
     * @return
     *     possible object is
     *     {@link ControlerType }
     *     
     */
    public ControlerType getControler() {
        return controler;
    }

    /**
     * Sets the value of the controler property.
     * 
     * @param value
     *     allowed object is
     *     {@link ControlerType }
     *     
     */
    public void setControler(ControlerType value) {
        this.controler = value;
    }

    /**
     * Gets the value of the planCalcScore property.
     * 
     * @return
     *     possible object is
     *     {@link PlanCalcScoreType }
     *     
     */
    public PlanCalcScoreType getPlanCalcScore() {
        return planCalcScore;
    }

    /**
     * Sets the value of the planCalcScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanCalcScoreType }
     *     
     */
    public void setPlanCalcScore(PlanCalcScoreType value) {
        this.planCalcScore = value;
    }

}
