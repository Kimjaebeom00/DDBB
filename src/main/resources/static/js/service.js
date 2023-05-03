var projectInfoInit = function() {
    var projectId = $('#projectId').val();

    currentProject = document.getElementById(projectId);
    api.projectMembers(projectId);//api.js호출한것이다
}