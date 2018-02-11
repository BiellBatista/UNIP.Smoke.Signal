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

package br.com.unip.cc.hosting;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * Esta classe representa o servidor do audio
 * 
 * @version 0.1 26/Abr/2017
 * @author Anderson Schinaid
 */

public class AudioServer {
	private ServerSocket MyService =null;
	private Socket ClientSocket = null;
	private InputStream input = null;
	private TargetDataLine targetDataLine = null;
	private OutputStream out = null;
	private AudioFormat audioFormat = null;
	private Mixer mixer = null;
	private Mixer mixer1 = null;
	private Mixer.Info[] mixerInfo = null;
	private DataLine.Info dataLineInfo = null;
	private SourceDataLine sourceDataLine = null;
	private int size = 0;
	private byte tempBuffer[] = null;

	public AudioServer() {
		mixerInfo = AudioSystem.getMixerInfo();
		size = 10000;
		tempBuffer = new byte [size];
		//Informando o tipo de equipamento e recebendo informa��o do socket
		try {
			mixer = AudioSystem.getMixer(mixerInfo[1]);
			audioFormat = getAudioFormat();
			dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();

			MyService = new ServerSocket(500);
			ClientSocket = MyService.accept();

			captureAudio();

			input = new BufferedInputStream(ClientSocket.getInputStream());
			out = new BufferedOutputStream(ClientSocket.getOutputStream());

			while (input.read(tempBuffer) != -1)
				sourceDataLine.write(tempBuffer, 0, size);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 8000.0F;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = false;

		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	private void captureAudio() {
		try {
			audioFormat = getAudioFormat();
			new DataLine.Info(TargetDataLine.class, audioFormat);

			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				mixer1 = AudioSystem.getMixer(mixerInfo[3]); // Select Available Hardware Devices for the micro, for my Notebook it is number 3

				if (mixer1.isLineSupported(dataLineInfo))
					targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
			}

			targetDataLine.open(audioFormat);
			targetDataLine.start();

			Thread captureThread = new CaptureThread();
			captureThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class CaptureThread extends Thread {
		byte tempBuffer[] = new byte[size];

		public void run() {
			try {
				while (true) {
					targetDataLine.read(tempBuffer, 0, tempBuffer.length);
					out.write(tempBuffer);
					out.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}