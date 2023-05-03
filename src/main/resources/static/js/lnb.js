let currentProject;
function checkSelectdProject(e) {
    let target = e.target;

    if(e.target.localName != 'li'){
        target = e.target.parentElement;
    }

    currentProject.classList.remove('selected');
    target.className = 'selected';
    currentProject = target;
}

const el = document.getElementById("projects");
el.addEventListener("click", checkSelectdProject, false);

function selectProject(e) {

}