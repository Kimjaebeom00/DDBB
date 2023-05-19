let projectInfoInit = function() {
    let projectId = $('#currentProjectId').val();
    let target = $('#projectForm-'+projectId);

    target.addClass('selected');

    let comparedCodeInfoMap = api.compareCode(projectId);
    console.log(comparedCodeInfoMap);
    const beforeCodeContainer = $('.before_code');
    const currentCodeContainer = $('.current_code');

    for(let i=0; i<comparedCodeInfoMap.beforeList.length; i++) {
        if(comparedCodeInfoMap.codeMap.before.add.indexOf(i) >= 0) {
            beforeCodeContainer.append('<p class="added_code">&nbsp;</p>');
            comparedCodeInfoMap.codeMap.before.add.shift();
            --i;
            continue;
        } else if(comparedCodeInfoMap.codeMap.before.modify.indexOf(i) >= 0) {
            beforeCodeContainer.append('<p class="modified_code">' + comparedCodeInfoMap.beforeList[i] + '</p>');
        } else if(comparedCodeInfoMap.codeMap.before.delete.indexOf(i) >= 0) {
            beforeCodeContainer.append('<p class="deleted_code">' + comparedCodeInfoMap.beforeList[i] + '</p>');
        } else {
            beforeCodeContainer.append('<p>' + comparedCodeInfoMap.beforeList[i] + '</p>');
        }
    }

    for(let i=0; i<comparedCodeInfoMap.currentList.length; i++) {
        if(comparedCodeInfoMap.codeMap.current.add.indexOf(i) >= 0) {
            currentCodeContainer.append('<p class="added_code">' + comparedCodeInfoMap.currentList[i] + '</p>');
        } else if(comparedCodeInfoMap.codeMap.current.modify.indexOf(i) >= 0) {
            currentCodeContainer.append('<p class="modified_code">' + comparedCodeInfoMap.currentList[i] + '</p>');
        } else if(comparedCodeInfoMap.codeMap.current.delete.indexOf(i) >= 0) {
            currentCodeContainer.append('<p class="deleted_code">&nbsp;</p>');
            comparedCodeInfoMap.codeMap.current.delete.shift();
            --i;
            continue;
        } else {
            currentCodeContainer.append('<p>' + comparedCodeInfoMap.currentList[i] + '</p>');
        }
    }
}

const goToInsertCode = function() {
    const projectId = $('#currentProjectId').val();
    const form = $('#InsertCodeForm');

    $('#projectIdForCode').val(projectId);

    form.submit();
}