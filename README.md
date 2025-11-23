# Omada Health Take Home Test
This is an app that uses the Flickr Api to display a list of photos. The app allows for the searching of the photos as well as displaying images in a grid view by default.

Tech stack: 
Retrofit, Coil, Hilt, MVI, Jetpack Compose, Kotlin

app:domain package was added in order to separate ui and data interaction.

Recent Images | Searched Images
:---------:|:---------:
<img width="1440" height="3120" alt="Screenshot_20251123_000050" src="https://github.com/user-attachments/assets/9534f0fc-aa08-4f02-8f96-655febfc9339" /> | <img width="1440" height="3120" alt="Screenshot_20251123_000251" src="https://github.com/user-attachments/assets/e6cd6847-9902-489a-b9a2-4e5c58ae85f1" />

Sources:
- LazyVerticalGrid: https://developer.android.com/develop/ui/compose/lists
- Hilt Gradle Setup: https://dagger.dev/hilt/gradle-setup
- Migrate from kapt to ksp: https://developer.android.com/build/migrate-to-ksp (Needed this for the hilt compiler dependency)

AI Usage:
- https://gemini.google.com/share/a9622ff43ad2
- The networkResultHandler function was also created with AI for optimizing api calls

Recording:
- App: https://drive.google.com/file/d/1kvul0RcUBk5yHtMWDwQTxNb__n85PWfO/view?usp=sharing

Missing:
- Recording of session
- Bonus requirements
- No pagination

Transparency on AI usage within project due to no recoding (I'm kind of new to the recording session form of interviewing): 
- Creating dependency injection modules in Hilt
- Creating AuthInterceptor class for hiding API_KEY (Was going to use BuildConfig.API_KEY in repository functions. Went with this option because it also hid the "format" and "nojsoncallback" queries)
- Cleaned up composables. Images weren't properly cropped.
- Optimization of three composables: ErrorContent(), LoadingContent(), SuccessContent()
- Using the `val searchQuery/var _searchQuery` backing property and how to properly use operators in init{} view model. Wanted to optimize user events (Was using a Launched Effect before and an immutable variable)
- Creating a mapper for mapping response to domain models. Wanted to create a ui -> domain/data -> domain architecture.

