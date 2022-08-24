package com.example.DLChordsTT.features.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomponavigation.screens.FirstScreen
import com.example.jetpackcomponavigation.screens.SecondScreen

@Composable
fun NavigationHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigations.FirstScreen.route){
        composable(route=AppNavigations.FirstScreen.route){
            FirstScreen(navController)
        }
        composable(AppNavigations.SecondScreen.route + "/{text}" ,
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            }
            )){
            SecondScreen(navController,it.arguments?.getString("text"))
        }

    }

}