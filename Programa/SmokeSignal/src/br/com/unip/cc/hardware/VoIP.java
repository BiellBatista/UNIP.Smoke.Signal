/* %W% %E% Gabriel Batista
 *
 * Copyright (c) 2000-2012 Raz�o Social Ltda. Todos os direitos reservados.
 *
 * Esse software � informa��o confidencial e propriet�ria da Raz�o
 * Social Ltda. ("Informa��o Confidencial"). Voc� n�o deve divulgar
 * tal Informa��o Confidencial e deve us�-la apenas em concord�ncia
 * com os termos de acordo de licen�a que voc� entrou com a Laranja Games Ltda.
 *
 * 
 * LARANJA GAMES LTDA N�O FAZ REPRESENTA��O OU D� GARANTIAS SOBRE A
 * ADEQUA��O DO SOFTWARE, SEJA EXPRESSA OU IMPLICADA, INCLUINDO MAS
 * N�O LIMITANDO AS GARANTIAS DE COMERCIALIZA��O, ADEQUA��O PARA UM
 * DETERMINADO FIM PARTICULAR OU N�O-VIOLA��O. LARANJA GAMES LTDA N�O
 * SER� RESPONS�VEL POR QUALQUER DANO SOFRIDO PELO LICENCIADO EM
 * DECORR�NCIA DO USO, MODIFICA��O OU DISTRIBUI��O DESSE SOFTWARE OU
 * SEUS DERIVADOS.
 */

package br.com.unip.cc.hardware;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * Esta classe representa a captura do audio e usa instru��es de sockets para enviar audio para o servidor
 * 
 * @version 0.1 26/Abr/2017
 * @author Anderson Schinaid
 */

public class VoIP {
	private ByteArrayOutputStream byteArrayOutputStream = null;
	private AudioFormat audioFormat = null;
	private TargetDataLine targetDataLine = null;
	private BufferedOutputStream out = null;
	private BufferedInputStream in = null;
	private Socket sock = null;
	private SourceDataLine sourceDataLine = null;
	private Mixer.Info[] mixerInfo = null;
	private Mixer mixer = null;
	private DataLine.Info dataLineInfo = null;
	private  DataLine.Info dataLineInfo1 = null;
	private Thread captureThread = null;
	private Thread playThread = null;
	private boolean stopCapture = false;

	public VoIP() {
		try {
			sock = new Socket("25.2.100.155", 500);
			out = new BufferedOutputStream(sock.getOutputStream());//escrevendo a informa��o no socket
			in = new BufferedInputStream(sock.getInputStream());//lendo a informa��o no software
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void captureAudio() {
		mixerInfo = AudioSystem.getMixerInfo();//Instanciando um Objeto do tipo Audio do sistema
		mixer = AudioSystem.getMixer(mixerInfo[3]); //Qual o tipo de Hardware de som e suas info

		audioFormat = getAudioFormat();
		dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat); //busca as informa��es de �udio

		try {
			targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
			targetDataLine.open(audioFormat);//abrindo o formato do audio
			targetDataLine.start();//iniciando o DataLine

			captureThread = new CaptureThread();
			captureThread.start();
			
			dataLineInfo1 = new DataLine.Info(SourceDataLine.class, audioFormat);
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		playThread = new PlayThread();
		playThread.start();
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 8000.0F;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = false;

		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	private class CaptureThread extends Thread {
		byte tempBuffer[] = new byte[10000];

		public void run() {
			byteArrayOutputStream = new ByteArrayOutputStream();
			stopCapture = false;

			try {
				while (!stopCapture) {
					int cnt = targetDataLine.read(tempBuffer, 0, tempBuffer.length);

					out.write(tempBuffer);

					if (cnt > 0)
						byteArrayOutputStream.write(tempBuffer, 0, cnt);
				}

				byteArrayOutputStream.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private class PlayThread extends Thread {
		byte tempBuffer[] = new byte[10000];

		public void run() {
			try {
				while (in.read(tempBuffer) != -1)
					sourceDataLine.write(tempBuffer, 0, 10000);

				sourceDataLine.drain();
				sourceDataLine.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}