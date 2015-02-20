package it.polito.elite.enocean.enj.eep;

import java.io.Serializable;

public class EEPIdentifier implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rorg
	 * @param function
	 * @param type
	 */
	public EEPIdentifier(Rorg rorg, byte function, byte type)
	{
		super();
		this.rorg = rorg;
		this.function = function;
		this.type = type;
	}

	// Identify the Radio-Telegram organization
	Rorg rorg;

	// Funcion of the device
	byte function;

	// Type of device
	byte type;

	/**
	 * @return the rorg
	 */
	public Rorg getRorg()
	{
		return rorg;
	}

	/**
	 * @param rorg
	 *            the rorg to set
	 */
	public void setRorg(Rorg rorg)
	{
		this.rorg = rorg;
	}

	/**
	 * @return the function
	 */
	public byte getFunction()
	{
		return function;
	}

	/**
	 * @param function
	 *            the function to set
	 */
	public void setFunction(byte function)
	{
		this.function = function;
	}

	/**
	 * @return the type
	 */
	public byte getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(byte type)
	{
		this.type = type;
	}

	public static EEPIdentifier parse(String eepIdentifierAsString)
	{
		EEPIdentifier identifier = null;
		// check the right lenght
		if ((eepIdentifierAsString.length() == 6)
				|| (eepIdentifierAsString.length() == 4))
		{
			// parses the eep identifier expressed according to the EEP
			// specification, i.e., rrfftt
			byte rorg = Byte.parseByte("0x"
					+ eepIdentifierAsString.substring(0, 2));
			byte func = Byte.parseByte("0x"
					+ eepIdentifierAsString.substring(2, 4));

			// TODO handle higher EEP e.g. D201
			byte type = (byte) 0xff;
			if (eepIdentifierAsString.length() == 6)
			{
				type = Byte.parseByte("0x"
						+ eepIdentifierAsString.substring(4, 6));
			}

			identifier = new EEPIdentifier(new Rorg(rorg), func, type);
		}

		return identifier;
	}

	public static boolean isPartOf(EEPIdentifier part, EEPIdentifier whole)
	{
		// checks if the part and the whole have the same rorg and function
		return ((part.rorg.getRorgValue() == whole.rorg.getRorgValue()) && (part.function == whole.function));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + function;
		result = prime * result + ((rorg == null) ? 0 : rorg.hashCode());
		result = prime * result + type;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof EEPIdentifier))
		{
			return false;
		}
		EEPIdentifier other = (EEPIdentifier) obj;
		if (function != other.function)
		{
			return false;
		}
		if (rorg == null)
		{
			if (other.rorg != null)
			{
				return false;
			}
		}
		else if (!rorg.equals(other.rorg))
		{
			return false;
		}
		if (type != other.type)
		{
			return false;
		}
		return true;
	}

	


}