# ViewPagerIndicator

좌우 반복이 가능한 ViewPager 입니다.

1,2,3 이 순서대로 ViewPager에 들어가 있다면 

RollingAdpater : 3->1->2->3->1 순으로 스와이프가 가능
IndicatorView : 현재 위치가 표시되는 인디케이터
AutoRollingManager : 액티비티의 생명주기에 따라 자동 스와이프 조절
액티비티에서 아래 코드 추가하여 사용!
    @Override
    protected void onPause() {
        super.onPause();
        mAutoRollingManager.onRollingStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoRollingManager.onRollingStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAutoRollingManager.onRollingDestroy();
    }
