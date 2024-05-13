# Algo Navigation

Compose Navigation 라이브러리의 동작을 분석하고, 단순화하여 구현한 학습용 오픈소스입니다.

### 구현 현황
- [x] 현재 NavBackStack 렌더링
- [x] composable 확장함수
- [x] navigation 확장함수
- [x] navigate
- [x] popBackStack
- [x] arguments 정의 및 전달
- [ ] rememberSaveable (NavController)
- [x] deep link
- [ ] viewModelStoreOwner

### 노트

- NavController의 rememberSaveable을 구현하기 위해서는 Navigator와 NavBackStackEntry를 Bundle에 저장하고, Bundle에서 복구할 방법을 구현해야 한다.
- viewModelStore에서 ViewModel을 생성할 때 extra에서 가져온 arguments를 savedStateHandle에 넣어준다. NavBackStackEntry는 HasDefaultViewModelProviderFactory를 구현하여 defaultViewModelCreationExtras를 override 한다.
