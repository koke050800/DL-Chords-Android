# DL-Chords-Android
 This Android application was developed in collaboration with my classmates [Carlos](https://github.com/CarlitosObr) and [Fernando](https://github.com/CarlitosObr).

DL Chords refers to "Deep Learning Chords". We decided to name our application this way, because through the use of neural networks, we obtain the musical chords from some audio stored in our mobile device. 

### What does this mobile application do?
This application obtains the musical chords and the lyrics (in Spanish) from some audio stored in the mobile device.

### How does it do it?
This application only works as a front-end, all audio processing (getting the chords and lyrics from an audio), are carried out through a server built with Python and FlaskApp. The server code is located in my repository [DL-Chords-Backend](https://github.com/koke050800/DL-Chords-Backend) and the explanation of chord recognition and audio lyrics can be found in the "[README.md](https://github.com/koke050800/DL-Chords-Backend#readme)" of this repository. 

The application connects to the server through Retrofit, sends the audio to be processed, then receives the result of the processing and finally displays this response to the user through a pdf file.
