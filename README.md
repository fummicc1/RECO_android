# RECO_android
[DRAFT] my first original android-kotlin app

# Architecture
- MVVM + Repository.

- Image from [official documentation](https://developer.android.com/jetpack/guide?hl=ja)

<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png?hl=ja" width=70%>

## View
- In android application, `Fragment` usually plays a part in `View`.

## ViewModel
- Each `fragment` has one `ViewModel`.
- `ViewModel` stores what users want to see.
- `Viewmodel` depends on `Repository`.

## Repository
- Accessible to outside of application such as `Database` and `WebServices`.
- Handle whether to fetch from cache or network.
- Store fetched data from network to reuse data if cache is preferable.

## Skill Stacks (WIP)
- Intent
- Fragment
- Navigation Graph
- ViewModel
- LiveData
- Coroutine
- Flow
### Third Party Library
- Firebase
    - Auth
    - Firestore
    - Dynamic Links