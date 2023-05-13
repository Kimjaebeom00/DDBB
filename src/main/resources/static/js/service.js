let projectInfoInit = function() {
    let projectId = $('#currentProjectId').val();
    let target = $('#projectForm-'+projectId);

    target.addClass('selected');


    // var projectMembers = api.projectMembers(projectId);//api.js호출한것이다
}

const goToInsertCode = function() {
    const projectId = $('#currentProjectId').val();
    const form = $('#InsertCodeForm');

    $('#projectIdForCode').val(projectId);

    form.submit();
}