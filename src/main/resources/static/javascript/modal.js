const modals = document.querySelectorAll(".modal");

const closeButtons = document.querySelectorAll(".close");

console.log("I am live");

for(var j = 0; j < closeButtons.length; j++) {

    closeButtons[j].onclick = function() {

        for(var k = 0; k < modals.length; k++) {

            modals[k].style.display = "none";
        }
    };
}

function closeAllModals() {
    const modals = document.querySelectorAll(".modal");

    for(let i = 0; i < modals.length; i++) {
        modals[i].style.display = "none";
    }
}

function openModal(topicId) {

    const modals = document.querySelectorAll(".modal");

    // Get the modal
    var modal = modals[parseInt(topicId)-1];
    var modalSingle;

    if(modal == null) {

        modalSingle = document.querySelector(".modal");
        modalSingle.style.display = "block";
        
    } else {
        
        modal.style.display = "block";
    }

    var spans = document.getElementsByTagName("span");

    for(var i = 0; i < spans.length; i++) {
        if(spans[i].getAttribute("class").match(modal.getAttribute("class"))) {
            spans[i].onclick = function () {
                modal.style.display = "none";
            }
            break;
        }
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}