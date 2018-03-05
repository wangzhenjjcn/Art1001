var $navul = document.getElementsByClassName( 'nav_ul' ),
    $pagol = document.getElementsByClassName( 'pag_ol' ),
    $pale  = document.getElementsByClassName( 'pa_left'),
    $pari  = document.getElementsByClassName( 'pa_right'),
    inDex  = 0;
    /*获取 gain*/

function onnavli(){

    var li = $navul[0].getElementsByTagName( 'li' ),
        lis= $pagol[0].getElementsByTagName( 'li' );

    for( var i=0;i<li.length;i++ ) {
        li[i].onclick = function () {
            for( var k=0;k<li.length;k++) {
                li[k].className = '';
            }
            this.className = 'nav_our';
        };
    }
    for( var j=0;j<lis.length;j++ ) {
        lis[j].index = j;
        lis[j].onclick = function () {
            for( var k=0;k<lis.length;k++) {
                lis[k].className = '';
            }
            inDex = this.index;
            this.className = 'pag_on';
        };
    }
    /*change the selected style*/

    $pale[0].onclick = function(){
        inDex--;
        if( inDex <= 0 ){
            inDex = 0
        }
        for( var k=0;k<lis.length;k++) {
            lis[k].className = '';
        }
        lis[inDex].className = 'pag_on';
    };
    $pari[0].onclick = function(){
        inDex++;
        if( inDex >= 5 ){
            inDex = 4
        }
        for( var k=0;k<lis.length;k++) {
            lis[k].className = '';
        }
        lis[inDex].className = 'pag_on';
    }

}

onnavli();
