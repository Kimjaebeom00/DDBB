let currentProject;
function selectProject(e) {
    let target = e.target;

    if(target.localName != 'li'){
        target = target.parentElement;
    }

    let form = target.parentElement;
    form.submit();
}

const el = document.getElementById("projects");
el.addEventListener("click", selectProject, false);
