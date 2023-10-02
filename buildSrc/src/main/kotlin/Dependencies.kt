package pl.piotrgorny.buildsrc

object Modules {
    object Feature {
        const val filterSetup = ":feature:filterSetup"
        const val reminder = ":feature:reminder"
    }
    object Core {
        const val model = ":core:model"
        const val database = ":core:database"
        const val common = ":core:common"
        const val mvi = ":core:mvi"
        const val data = ":core:data"
        const val ui = ":core:ui"
        const val navigation = ":core:navigation"
    }
}

object Libraries {
    object AndroidX {
        object Core {
            private const val version = "1.10.1"
            const val ktx = "androidx.core:core-ktx:$version"
        }

        object LifeCycle {
            private const val version = "2.6.1"
            const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"

        }

        object Compose {
            private const val version = "1.4.3"
            const val compilerVersion = version
            const val activity = "androidx.activity:activity-compose:1.7.1"
            const val navigation = "androidx.navigation:navigation-compose:2.5.3"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val material = "androidx.compose.material:material:$version"
            const val material3 = "androidx.compose.material3:material3:1.1.2"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.1"

            object Ui {
                const val ui = "androidx.compose.ui:ui:$version"
                const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
                const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            }
        }

        const val corutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"

        object Room {
            private const val version = "2.5.1"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val paging = "androidx.room:room-paging:$version"
        }

        object Paging {
            private const val version = "3.1.1"
            const val runtime = "androidx.paging:paging-runtime:$version"
            const val compose = "androidx.paging:paging-compose:1.0.0-alpha19"
        }
    }

    const val jodaTime = "joda-time:joda-time:2.12.2"
    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    const val gson = "com.google.code.gson:gson:2.8.8"
    const val glide = "com.github.bumptech.glide:glide:3.5.2"
    const val landscapist = "com.github.skydoves:landscapist-glide:2.2.9"

    object Test {
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
        const val truth = "com.google.truth:truth:1.1.3"
        const val coreTesting = "android.arch.core:core-testing:1.1.1"
        object JUnit {
            private const val junit_version = "4.13.2"
            private const val junit_ext_version = "1.1.5"
            const val junit = "junit:junit:$junit_version"
            const val junitExt = "androidx.test.ext:junit:$junit_ext_version"
        }

        object Compose {
            private const val version = "1.4.3"
            const val jUnit = "androidx.compose.ui:ui-test-junit4:$version"
            const val testManifest = "androidx.compose.ui:ui-test-manifest:$version"
        }
    }
}
