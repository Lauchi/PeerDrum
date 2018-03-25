/*midi note assignment
0: Kick
1: Snare
2: closedHH
3: openHH
4: Clave
*/

s = Server.local.boot;

s.waitForBoot
(
{
	//read samples
	PathName.new("/Users/simonheiss/IdeaProjects/PeerDrum/samples/808").entries
	.do
	(
		{
			|item, index|
			Buffer.readChannel(s,item.fullPath,channels:0,bufnum:index);
		}
	);


	//add sample player to server
	SynthDef(\playBuf,
		{
			|buffer=0, amp=0.5, pan=0, speed=1, out=0|
			var signal, rate, phase, numFrames, numChannels, envelope,sr;

			sr=SampleRate.ir;

			numChannels=BufChannels.kr(buffer);
			numFrames=BufFrames.kr(buffer);
			phase=Phasor.ar(1,BufRateScale.ir(buffer)*speed,0,numFrames);

			envelope=Line.kr(0,1,(numFrames-32)/sr,doneAction:2);

			signal=BufRd.ar
			(
				//numChannels:numChannels,
				numChannels:1,
				numChannels:1,
				bufnum:buffer,
				phase:phase,
				loop:0,
				interpolation:2,
			);

			signal=Pan2.ar(signal,pan,amp);
			Out.ar(out,signal);
		}
	).add;
	{
		SynthDef(\master,
			{
				|
				in,
				out=0,
				reverbWet=0.3,
				distortion=0.0001
				|
				var signal;
				signal=In.ar(in,2);

				//reverb
				/*
				signal=GVerb.ar
				(
					signal,
					roomsize: 80,
					revtime: 4.89,
					damping: 0.41,
					inputbw: 0.19,
					drylevel: -3,
					earlyreflevel: -40,
					taillevel: -24
				);
				*/

				signal=FreeVerb2.ar
				(
					signal[0],
					signal[1],
					mix:0.1,
					room:0.8,
					damp:0.5,
				);

				//compression
				signal=Compander.ar
				(
					signal, signal,
					        thresh: 0.9,
					        slopeBelow: 1,
					        slopeAbove: 0.5,
					        clampTime:  0.01,
					        relaxTime:  0.01
				    );

				Out.ar(out,signal);
			}
		).add;

		s.sync;

		~masterBus=Bus.audio(s,2);
		~masterSynth=Synth(\master, [\in,~masterBus], addAction:\addToTail);
	}.fork;
	//prepare midi things
	MIDIClient.init;
	//MIDIClient.sources;
	MIDIIn.connectAll;

	MIDIdef.noteOn(\fromJava,
		{
			|val, num, chan, src|
			Synth(\playBuf,[\buffer,num,\out,~masterBus],addAction:\addToHead);
		},
	);
}
)


//~masterSynth.set(\distortion,0.01)