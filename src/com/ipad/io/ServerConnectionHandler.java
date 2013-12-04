/*
* Copyright (C) 2007-2009 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
*
* This file is part of GSAN, an integrated service management system for Sanitation
*
* GSAN is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License.
*
* GSAN is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
*/

/*
* GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
* Copyright (C) <2007> 
* Adriano Britto Siqueira
* Alexandre Santos Cabral
* Ana Carolina Alves Breda
* Ana Maria Andrade Cavalcante
* Aryed Lins de Araújo
* Bruno Leonardo Rodrigues Barros
* Carlos Elmano Rodrigues Ferreira
* Clêudio de Andrade Lira
* Denys Guimarães Guenes Tavares
* Eduardo Breckenfeld da Rosa Borges
* Fabíola Gomes de Araújo
* Flêvio Leonardo Cavalcanti Cordeiro
* Francisco do Nascimento Júnior
* Homero Sampaio Cavalcanti
* Ivan Sérgio da Silva Júnior
* José Edmar de Siqueira
* José Thiago Tenório Lopes
* Kássia Regina Silvestre de Albuquerque
* Leonardo Luiz Vieira da Silva
* Márcio Roberto Batista da Silva
* Maria de Fátima Sampaio Leite
* Micaela Maria Coelho de Araújo
* Nelson Mendonça de Carvalho
* Newton Morais e Silva
* Pedro Alexandre Santos da Silva Filho
* Rafael Corrêa Lima e Silva
* Rafael Francisco Pinto
* Rafael Koury Monteiro
* Rafael Palermo de Araújo
* Raphael Veras Rossiter
* Roberto Sobreira Barbalho
* Roberto Souza
* Rodrigo Avellar Silveira
* Rosana Carvalho Barbosa
* Sávio Luiz de Andrade Cavalcante
* Tai Mu Shih
* Thiago Augusto Souza do Nascimento
* Tiago Moreno Rodrigues
* Vivianne Barbosa Sousa
*
* Este programa é software livre; você pode redistribuí-lo e/ou
* modificá-lo sob os termos de Licença Pública Geral GNU, conforme
* publicada pela Free Software Foundation; versão 2 da
* Licença.
* Este programa é distribuído na expectativa de ser útil, mas SEM
* QUALQUER GARANTIA; sem mesmo a garantia implêcita de
* COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
* PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
* detalhes.
* Você deve ter recebido uma cópia da Licença Pública Geral GNU
* junto com este programa; se não, escreva para Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
* 02111-1307, USA.
*/   

package com.ipad.io;

// Copyright 2004 Nokia Corporation.
//
// THIS SOURCE CODE IS PROVIDED 'AS IS', WITH NO WARRANTIES WHATSOEVER,
// EXPRESS OR IMPLIED, INCLUDING ANY WARRANTY OF MERCHANTABILITY, FITNESS
// FOR ANY PARTICULAR PURPOSE, OR ARISING FROM A COURSE OF DEALING, USAGE
// OR TRADE PRACTICE, RELATING TO THE SOURCE CODE OR ANY WARRANTY OTHERWISE
// ARISING OUT OF ANY PROPOSAL, SPECIFICATION, OR SAMPLE AND WITH NO
// OBLIGATION OF NOKIA TO PROVIDE THE LICENSEE WITH ANY MAINTENANCE OR
// SUPPORT. FURTHERMORE, NOKIA MAKES NO WARRANTY THAT EXERCISE OF THE
// RIGHTS GRANTED HEREUNDER DOES NOT INFRINGE OR MAY NOT CAUSE INFRINGEMENT
// OF ANY PATENT OR OTHER INTELLECTUAL PROPERTY RIGHTS OWNED OR CONTROLLED
// BY THIRD PARTIES
//
// Furthermore, information provided in this source code is preliminary,
// and may be changed substantially prior to final release. Nokia Corporation
// retains the right to make changes to this source code at
// any time, without notice. This source code is provided for informational
// purposes only.
//
// Nokia and Nokia Connecting People are registered trademarks of Nokia
// Corporation.
// Java and all Java-based marks are trademarks or registered trademarks of
// Sun Microsystems, Inc.
// Other product and company names mentioned herein may be trademarks or
// trade names of their respective owners.
//
// A non-exclusive, non-transferable, worldwide, limited license is hereby
// granted to the Licensee to download, print, reproduce and modify the
// source code. The licensee has the right to market, sell, distribute and
// make available the source code in original or modified form only when
// incorporated into the programs developed by the Licensee. No other
// license, express or implied, by estoppel or otherwise, to any other
// intellectual property rights is granted herein.


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import com.ipad.util.Util;

//import example.btsppecho.MIDletApplication;
//import example.btsppecho.LogScreen;


public class ServerConnectionHandler
    implements Runnable
{
    private final static byte ZERO = (byte) '0';
    private final static int LENGTH_MAX_DIGITS = 5;

    // this is an arbitrarily chosen value:
    private final static int MAX_MESSAGE_LENGTH =
                             65536 - LENGTH_MAX_DIGITS;

    private final ServiceRecord serviceRecord;
    private final int requiredSecurity;
    private final ServerConnectionHandlerListener listener;
    private final Hashtable sendMessages = new Hashtable();

    private StreamConnection connection;
    private OutputStream out;
    private InputStream in;
    private volatile boolean aborting;

    public ServerConnectionHandler(
               ServerConnectionHandlerListener listener,
               ServiceRecord serviceRecord,
               int requiredSecurity)
    {
        this.listener = listener;
        this.serviceRecord = serviceRecord;
        this.requiredSecurity = requiredSecurity;
        aborting = false;

        connection = null;
        out = null;
        in = null;
        listener = null;

        // the caller must call method 'start'
        // to start the reader and writer
    }

    public ServiceRecord getServiceRecord()
    {
        return serviceRecord;
    }


    public synchronized void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }


    public void close()
    {
        if (!aborting)
        {
            synchronized(this)
            {
                aborting = true;
            }

            synchronized(sendMessages)
            {
                sendMessages.notify();
            }

            if (out != null)
            {
                try
                {
                    out.close();
                    synchronized (this)
                    {
                        out = null;
                    }
                }
                catch(IOException e)
                {
                	e.printStackTrace();
                    // there is nothing we can do: ignore it
                }
            }

            if (in != null)
            {
                try
                {
                    in.close();
                    synchronized (this)
                    {
                        in = null;
                    }
                }
                catch(IOException e)
                {
                	e.printStackTrace();
                    // there is nothing we can do: ignore it
                }
            }

            if (connection != null)
            {
                try
                {
                    connection.close();
                    synchronized (this)
                    {
                        connection = null;
                    }
                }
                catch (IOException e)
                {
                    // there is nothing we can do: ignore it
                }
            }
        }
    }



    public void queueMessageForSending(Integer id, byte[] data)
    {
        if (data.length > MAX_MESSAGE_LENGTH)
        {
            throw new IllegalArgumentException(
                          "Message too long: limit is " +
                          MAX_MESSAGE_LENGTH + " bytes");
        }

        synchronized(sendMessages)
        {
            sendMessages.put(id, data);
            sendMessages.notify();
        }
    }

    public void run()
    {
        // the reader

        // 1. open the connection and streams, start the writer
        String url = null;
        try
        {
            // 'must be master': false
            url = serviceRecord.getConnectionURL(
                                  requiredSecurity,
                                  false);

            connection = (StreamConnection) Connector.open(url);
            in = connection.openInputStream();
            out = connection.openOutputStream();

            //LogScreen.log("Opened connection & streams to: '" +
            //          url + "'\n");

            // start the writer
            Writer writer = new Writer(this);
            Thread writeThread = new Thread(writer);
            writeThread.start();

            //LogScreen.log("Started a reader & writer for: '" +
            //          url + "'\n");

            // open succeeded, inform listener
            listener.handleOpen(this);
        }
        catch(IOException e)
        {
        	e.printStackTrace();
            // open failed, close any connections/streams, and
            // inform listener that the open failed

            //LogScreen.log("Failed to open " +
            //              "connection or streams for '" +
            //               url + "' , Error: " +
            //               e.getMessage());

            close();

            listener.handleOpenError(
                         this,
                         "IOException: '" + e.getMessage() + "'");

            return;
        }
        catch (SecurityException e)
        {
            close();
            listener.handleOpenError(this, "SecurityException: '" + e.getMessage() + "'");
// Daniel - Security Excepiton    		
//    		Util.mostrarErro("Acesso de leitura não  autorizado pelo usuario! A aplicação será fechada!");		    	
            Util.mostrarErro("Acesso de leitura não  autorizado pelo usuario!");		    	
//    		Fachada.getInstancia().exit();

            return;
        }
        
        while (!aborting)
        {
            int length = 0;
            try
            {
                byte[] lengthBuf = new byte[LENGTH_MAX_DIGITS];
                readFully(in, lengthBuf);
                length = readLength(lengthBuf);
                byte[] temp = new byte[length];
                readFully(in, temp);
                //LogScreen.log("temp: "+temp+"\n");
                listener.handleReceivedMessage(this, temp);
            }
            catch (IOException e)
            {
                close();
                if (length == 0)
                {
                   listener.handleClose(this);
                }
                else
                {
                   // we were in the middle of reading...
                   listener.handleErrorClose(this, e.getMessage());
                }
            }
        }
    }


    private static void readFully(InputStream in, byte[] buffer)
        throws IOException
    {
        int bytesRead = 0;
        //LogScreen.log("readFully - buffer:"+buffer.length+"\n");
        while (bytesRead < buffer.length)
        {
            //LogScreen.log("1 - aqui\n");
            int count = in.read(buffer,
                                bytesRead,
                                buffer.length - bytesRead);

            if (count == -1)
            {
                throw new IOException("Input stream closed");
            }
            bytesRead += count;
        }
        //LogScreen.log("readFully - Saiu: "+ bytesRead +"\n");
    }


    private static int readLength(byte[] buffer)
    {
        int value = 0;

        for (int i = 0; i < LENGTH_MAX_DIGITS; ++i)
        {
            value *= 10;
            value += buffer[i] - ZERO;
        }
        return value;
    }


    private void sendMessage(OutputStream out, byte[] data)
        throws IOException
    {
        if (data.length > MAX_MESSAGE_LENGTH)
        {
            throw new IllegalArgumentException(
                          "Message too long: limit is: " +
                          MAX_MESSAGE_LENGTH + " bytes");
        }
//        String temp = "ANTHONY IMPRIMIU!!!!!!!";
//        byte[] d = new byte[temp.length()];
//        d = temp.getBytes();
        
        byte[] buf = new byte[LENGTH_MAX_DIGITS + data.length];
        writeLength(data.length, buf);
        System.arraycopy(data,
                         0,
                         buf,
                         LENGTH_MAX_DIGITS,
                         data.length);
        //LogScreen.log("\nData:"+temp1+" len: " + temp1.length() + "\n");
        //LogScreen.log("buf:"+buf[0]+"\n");
        out.write(buf);
        out.flush();
    }


    private static void writeLength(int value, byte[] buffer)
    {
        for (int i = LENGTH_MAX_DIGITS -1; i >= 0; --i)
        {
            buffer[i] = (byte) (ZERO + value % 10);
            value = value / 10;
        }
    }


    private class Writer
        implements Runnable
    {
        private final ServerConnectionHandler handler;


        Writer(ServerConnectionHandler handler)
        {
            this.handler = handler;
        }


        public void run()
        {
            while (!aborting)
            {
                synchronized(sendMessages)
                {
                    Enumeration e = sendMessages.keys();
                    if (e.hasMoreElements())
                    {
                        // send any pending messages
                        Integer id = (Integer) e.nextElement();
                        //LogScreen.log("MSG: " + String.valueOf(sendMessages.get(id)));
                        byte[] sendData =
                               (byte[]) sendMessages.get(id);
                        try
                        {
                            //LogScreen.log("\nAQUI PORRRAAAAAAAAAAAAAAAAAAAAAAAA\n");
                            //LogScreen.log("out:"+out.toString());
                            //LogScreen.log("\nsendData:"+sendData.toString());
                            sendMessage(out, sendData);

                            // remove sent message from queue
                            sendMessages.remove(id);

                            // inform listener that it was sent
                            listener.handleQueuedMessageWasSent(
                                         handler,
                                         id);
                        }
                        catch (IOException ex)
                        {
                            close(); // stop the networking thread

                            // inform that we got an error close
                            listener.handleErrorClose(handler,
                                                      ex.getMessage());
                        }
                    }

                    if (sendMessages.isEmpty())
                    {
                        try
                        {
                            sendMessages.wait();
                        }
                        catch (InterruptedException ex)
                        {
                            // this can't happen in MIDP: ignore it
                        }
                    }
                }
            }
        }
    }
}