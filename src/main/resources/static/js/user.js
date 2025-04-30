const terminateUserButton= document.getElementById("terminate-user-btn");

if (terminateUserButton) {
    terminateUserButton.addEventListener('click', e =>{
        let userId = document.getElementById("user-id").value;
        fetch(`/api/user/${userId}`,{
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                isActive: "N"
            })
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

const editUserButton= document.getElementById("edit-user-btn");
const checkNicknameButton= document.getElementById("check-nickname-btn");
const saveUserButton= document.getElementById("save-user-btn");
const cancelUserButton= document.getElementById("cancel-user-btn");

const nicknameLabel= document.getElementById("nickname-label");
const nicknameInput= document.getElementById("nickname-input");

normalMode();

if (editUserButton) {
    editUserButton.addEventListener('click', e =>{
        editMode();
    })
}

nicknameInput.addEventListener('keyup', function() {
    notAllowSaveMode();
});

if (checkNicknameButton) {
    checkNicknameButton.addEventListener('click', e =>{
        let oriNickname = document.getElementById("nickname-ori").value;
        let newNickname = nicknameInput.value;

        if (newNickname === "" || oriNickname === newNickname) {
            notAllowSaveMode();
        } else {
            const params = {
                nickname: newNickname
            };
            const queryString = new URLSearchParams(params).toString();

            fetch(`/api/userByNickname/?${queryString}`)
                .then((response) => {
                    if(response.ok) {
                        allowSaveMode();
                    } else {
                        notAllowSaveMode();
                    }
                })
        }
    })
}

if (saveUserButton) {
    saveUserButton.addEventListener('click', e =>{
        let userId = document.getElementById("user-id").value;
        fetch(`/api/user/${userId}`,{
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nickname: document.getElementById("nickname-input").value
            })
        }).then(() => {
            alert("수정 되었습니다");
            location.replace('/profile');
        })
        normalMode();
    })
}

if (cancelUserButton) {
    cancelUserButton.addEventListener('click', e =>{
        normalMode();
    })
}

function normalMode() {
    editUserButton.style.display = "inline";
    terminateUserButton.style.display = "inline";

    saveUserButton.style.display = "none";
    cancelUserButton.style.display = "none";

    nicknameLabel.style.display = "inline";
    nicknameInput.style.display = "none";
    checkNicknameButton.style.display = "none";
}

function editMode() {
    editUserButton.style.display = "none";
    terminateUserButton.style.display = "none";

    saveUserButton.style.display = "inline";
    cancelUserButton.style.display = "inline";
    notAllowSaveMode();

    nicknameLabel.style.display = "none";
    nicknameInput.style.display = "inline";
    checkNicknameButton.style.display = "inline";
}

function allowSaveMode() {
    saveUserButton.disabled = false;
    checkNicknameButton.disabled = true;
}

function notAllowSaveMode() {
    saveUserButton.disabled = true;
    checkNicknameButton.disabled = false;
}