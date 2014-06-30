/*
 * EnJ - EnOcean Java API
 * 
 * Copyright 2014 Andrea Biasi, Dario Bonino 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package it.polito.elite.enocean.protocol.serial.v3.network.link.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * A utility factory for getting a reference to the serial port used for
 * communicating with the physical EnOcean transceiver, initialized with the
 * correct parameters.
 * 
 * @author <a href="mailto:dario.bonino@gmail.com">Dario Bonino</a>
 * @authr <a href="mailto:biasiandrea04@gmail.com">Andrea Biasi </a>
 * 
 */
public class SerialPortFactory
{

	/**
	 * Provides a correctly configured serial port connection, given a port
	 * identifier and a transmission timeout.
	 * 
	 * @param portName
	 *            The name of the port to connect to (e.g., COM1, /dev/tty0,
	 *            ...)
	 * @param timeout
	 *            The connection timeout
	 * @return a {@link SerialPort} instance representing the port identified by
	 *         the given data, if existing, or null otherwise.
	 * @throws Exception
	 */
	public static SerialPort getPort(String portName, int timeout)
	{
		// the serial port reference, initially null
		SerialPort serialPort = null;

		try
		{

			// sets the port name (TODO: check if needed)
			System.setProperty("gnu.io.rxtx.SerialPorts", portName);

			// build a port identifier given the port id as a string
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(portName);

			// check that the port exists and is free
			if (portIdentifier.isCurrentlyOwned())
			{
				// TODO: add a logging system here
				System.out.println("Error: Port is currently in use");
			}
			else
			{
				// open the serial port
				CommPort commPort = portIdentifier.open(
						SerialPortFactory.class.getName(), timeout);

				// check that the just opened communication port is actually a
				// serial port.
				if (commPort instanceof SerialPort)
				{
					// store the serial port reference
					serialPort = (SerialPort) commPort;

					// set the serial port parameters according to the ESP3
					// specification:
					// speed = 57600 bps
					// data = 8 byte
					// stop bit = 1
					// parity = none
					serialPort.setSerialPortParams(57600,
							SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
				}
				else
				{
					// TODO: add a logging system here
					System.out.println("Error");
				}
			}
		}
		catch (UnsupportedCommOperationException | NoSuchPortException
				| PortInUseException e)
		{
			//TODO: add a logging system here
			e.printStackTrace();
		}

		return serialPort;
	}
}