package com.example.DLChordsTT.features.navigation


sealed class AppNavigations(val route:String){
    object FirstScreen:AppNavigations("FirstScreen")
    object SecondScreen:AppNavigations("SecondScreen")
    object StoragedMusicPlayer:AppNavigations("StoragedMusicPlayer")
    object ProcessedMusicPlayer:AppNavigations("ProcessedMusicPlayer")
    object CutScreen:AppNavigations("CutScreen")
    object GeneratedFiles:AppNavigations("GeneratedFiles")
    object ShowPDF:AppNavigations("ShowPDF")

}
