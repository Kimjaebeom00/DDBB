let projectInfoInit = function() {
    let projectId = $('#currentProjectId').val();
    let target = $('#projectForm-'+projectId);

    target.addClass('selected');

    let comparedCodeInfoMap = api.compareCode(projectId);
    console.log(comparedCodeInfoMap);
    const beforeCodeContainer = $('.before_code');
    const currentCodeContainer = $('.current_code');

    comparedCodeInfoMap.beforeList.forEach((beforeLine, index) => {
        if(comparedCodeInfoMap.modifiedList.indexOf(index) >= 0) {
            beforeCodeContainer.append('<p class="modified_code">' + beforeLine + '</p>');
        } else if(comparedCodeInfoMap.addedList.indexOf(index) >= 0) {
            beforeCodeContainer.append('<p class="added_code">' + beforeLine + '</p>');
        } else {
            beforeCodeContainer.append('<p>' + beforeLine + '</p>');
        }
    });
    comparedCodeInfoMap.currentList.forEach((currentLine, index) => {
        if(comparedCodeInfoMap.modifiedList.indexOf(index) >= 0) {
            currentCodeContainer.append('<p class="modified_code">' + currentLine + '</p>');
        } else if(comparedCodeInfoMap.addedList.indexOf(index) >= 0) {
            currentCodeContainer.append('<p class="added_code">' + currentLine + '</p>');
        } else {
            currentCodeContainer.append('<p>' + currentLine + '</p>');
        }
    });
}

const goToInsertCode = function() {
    const projectId = $('#currentProjectId').val();
    const form = $('#InsertCodeForm');

    $('#projectIdForCode').val(projectId);

    form.submit();
}