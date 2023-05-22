function goToInfo(projectId) {
    const form = document.getElementById('projectInfoForm');
    document.getElementById('projectId').value = projectId;
    form.submit();
}
function goToModify(projectId) {
    const form = document.getElementById('projectModifyForm');
    document.getElementById('projectId').value = projectId;
    form.action = "/modify.html";  // modify.html 페이지의 경로를 설정합니다
    form.method = "get";  // GET 방식으로 페이지를 요청합니다
    form.submit();
}
