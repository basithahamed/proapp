let AddProjectModel = (() => {

    let Project = function(id, projectName, projectDesc, status, fromDate, toDate, users, createdBy, percentage){
        this.id = id;
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.percentage = percentage;
        this.users = users;
        this.createdBy = createdBy;
    }
    let changeServerObject = function(serverObject){
        localStorage.lastAddedProject = serverObject.id;
        return new Project(serverObject.id, serverObject.projectName, serverObject.projectDesc, serverObject.status, serverObject.fromDate, serverObject.toDate, serverObject.users, serverObject.createdBy, serverObject.percentage);
    }
    let addProject = (projectName, projectDesc, fromDate, toDate, users) => {
        let formData = new FormData();
        let tempCreated = USERID;
        let tempObj = {
            projectName : projectName,
            projectDesc : projectDesc,
            fromDate : fromDate,
            toDate : toDate,
            users : users,
            createdBy : tempCreated
        }
        
        formData.append("data", JSON.stringify(tempObj));
        
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/ProApp/project/add");
        xhr.send(formData);
        xhr.onload = () => {
            let serverObject = JSON.parse(xhr.response); 
            let project = changeServerObject(serverObject);
            ProjectModel.addProject(project);
            ProjectController.openTaskAddingSection();
            ProjectView.renderProjects(ProjectModel.getProjectsArray());
        }
        // return new Project(getTempId(), projectName, projectDesc, fromDate, toDate, users);
    }
   
    return {
        addProject : addProject,
        changeServerObject : changeServerObject
    }
})();