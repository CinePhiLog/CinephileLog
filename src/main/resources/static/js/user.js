const terminateUserButton= document.getElementById("terminate-user-btn");

if (terminateUserButton) {
    terminateUserButton.addEventListener('click', e =>{
        let userId = document.getElementById("user-id").value;
        fetch(`/api/user/${userId}`,{
            method: 'PUT'
        }).then (() => {
            fetch(`/userLogout`,{
                method: 'POST'
            }).then(() => {
                alert("탈퇴 되었습니다");
                location.replace('/login');
            })
        })
    })
}