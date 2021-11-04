window.onload = function() {
    let elem = document.getElementsByClassName("post");
    let size = elem.length;
    let height = elem[0].offsetHeight;
    for(let i=1; i<size; i++){
        if(height < elem[i].offsetHeight){
            height = elem[i].offsetHeight;
        }
    }

    for(let i=0; i<size; i++){
        elem[i].style.height = `${height}px`;
    }
}