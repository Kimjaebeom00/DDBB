function goToInfo(projectId) {
    const form = document.getElementById('projectInfoForm');
    document.getElementById('projectId').value = projectId;
    form.submit();
}