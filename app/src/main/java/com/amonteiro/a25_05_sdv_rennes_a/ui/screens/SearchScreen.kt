package com.amonteiro.a25_05_sdv_rennes_a.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.amonteiro.a25_05_sdv_rennes_a.R
import com.amonteiro.a25_05_sdv_rennes_a.model.PictureBean
import com.amonteiro.a25_05_sdv_rennes_a.ui.MyError
import com.amonteiro.a25_05_sdv_rennes_a.ui.Routes
import com.amonteiro.a25_05_sdv_rennes_a.ui.theme._25_05_sdv_rennes_aTheme
import com.amonteiro.a25_05_sdv_rennes_a.viewmodel.MainViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Preview(showBackground = true, showSystemUi = true, name = "1")
@Preview(
    showBackground = true, showSystemUi = true, name = "2",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL, locale = "fr"
)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _25_05_sdv_rennes_aTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val mainViewModel = MainViewModel()
            //mainViewModel.loadFakeData(runInProgress=true, errorMessage="Une erreur")

            SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "1")
@Preview(
    showBackground = true, showSystemUi = true, name = "2",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL, locale = "fr"
)
@Composable
fun SearchScreenWithDataPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _25_05_sdv_rennes_aTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val mainViewModel = MainViewModel()
            mainViewModel.loadFakeData(runInProgress = true, errorMessage = "Une erreur")

            SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel)
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier,
                 mainViewModel: MainViewModel = viewModel(),
                 navHostController: NavHostController? = null) {


    //remember -> sauvegarde entre 2 rappel de SearchBar(recomposition)
    //mutableStateOf donnée observaable de compose
    var searchText = remember { mutableStateOf("") }
    val list = mainViewModel.dataList.collectAsStateWithLifecycle().value//.filter { it.title.contains(searchText.value, true) }
    val runInProgress = mainViewModel.runInProgress.collectAsStateWithLifecycle().value
    val errorMessage = mainViewModel.errorMessage.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Transforme donnée observable -> donnée observable pour compose
        SearchBar(
            searchText = searchText,
            onSearch = {mainViewModel.loadWeathers(searchText.value)})

        MyError(
            errorMessage = errorMessage
        )

        AnimatedVisibility(runInProgress) {
            CircularProgressIndicator()
        }



        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1354f)) {
            items(list.size) {
                PictureRowItem(data = list[it], navHostController = navHostController)
            }
        }

        Row {
            Button(
                onClick = { searchText.value = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.clear_filter))
            }

            Button(
                onClick = { mainViewModel.loadWeathers(searchText.value) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.bt_load))
            }
        }
    }
}


@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText: MutableState<String>, onSearch: () -> Unit = {}) {

    TextField(
        value = searchText.value, //Valeur affichée
        onValueChange = { newValue: String -> searchText.value = newValue }, //Nouveau texte entrée
        leadingIcon = { //Image d'icone
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { //Texte d'aide qui se déplace
            Text("Enter text")
            //Pour aller le chercher dans string.xml
            //Text(stringResource(R.string.placeholder_search))
        },
        //placeholder = { //Texte d'aide qui disparait
        //Text("Recherche")
        //},

        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // Définir le bouton "Entrée" comme action de recherche
        keyboardActions = KeyboardActions(onSearch = {
            onSearch()
        }), // Déclenche l'action définie
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable //Composable affichant 1 PictureBean
fun PictureRowItem(modifier: Modifier = Modifier, data: PictureBean, navHostController: NavHostController? = null) {

    val expended = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        //Permission Internet nécessaire
        GlideImage(
            model = data.url,
            //Pour aller le chercher dans string.xml
            //contentDescription = getString(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            // Image d'attente. Permet également de voir l'emplacement de l'image dans la Preview
            loading = placeholder(R.mipmap.ic_launcher_round),
            // Image d'échec de chargement
            failure = placeholder(R.mipmap.ic_launcher),
            contentScale = ContentScale.Fit,
            //même autres champs qu'une Image classique
            modifier = Modifier
                .heightIn(max = 100.dp) //Sans hauteur il prendra tous l'écran
                .widthIn(max = 100.dp)
                .clickable{
                    navHostController?.navigate(Routes.DetailRoute(data.id))
                }
        )
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .clickable {
                    expended.value = !expended.value
                }
        ) {
            Text(
                text = data.title,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = if (expended.value) data.longText else data.longText.take(20),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}