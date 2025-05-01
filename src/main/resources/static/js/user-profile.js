const editUserButton= document.getElementById("edit-user-btn");
const saveUserButton= document.getElementById("save-user-btn");
const cancelUserButton= document.getElementById("cancel-user-btn");
const terminateUserButton= document.getElementById("terminate-user-btn");

const nicknameText= document.getElementById("nickname-text");
const nicknameOri = document.getElementById("nickname-ori");
const nicknameInput= document.getElementById("nickname-input");
const nicknameMsg= document.getElementById("nickname-msg");
const checkedIcon = document.getElementById("checked-icon");
const checkNicknameButton= document.getElementById("check-nickname-btn");

defaultMode();

if (terminateUserButton) {
    terminateUserButton.addEventListener('click', e =>{
        const confirmed = confirm("Are you sure you want to delete your account? This action cannot be undone");
        if (confirmed) {
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
        }
    })
}

if (editUserButton) {
    editUserButton.addEventListener('click', e =>{
        editUserButton.style.display = "none";
        saveUserButton.style.display = "inline";
        saveUserButton.disabled = true;
        cancelUserButton.style.display = "inline";
        terminateUserButton.style.display = "none";

        //nickname - hide text and show input
        nicknameText.style.display = "none";
        nicknameInput.style.display = "inline";
        checkNicknameButton.style.display = "inline";
    })
}

let isNicknameChanged = false;

nicknameInput.addEventListener('keyup', function() {
    isNicknameChanged = false;
    checkedIcon.style.display = "none";
    nicknameMsg.style.display = "none";

    saveButtonEnableCheck();
});

if (checkNicknameButton) {
    checkNicknameButton.addEventListener('click', e =>{
        let oriNickname = nicknameOri.value.trim();
        let newNickname = nicknameInput.value.trim();

        nicknameMsg.style.display = "none";
        checkedIcon.style.display = "none";

        if (newNickname === "" || oriNickname === newNickname) {
            isNicknameChanged = false;
            saveButtonEnableCheck();
        } else {
            fetch("/api/checkNickname", {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain"
                },
                body: newNickname
            })
            .then(response => response.text())
            .then(result => {
                const isAvailable = result === "true";
                if (isAvailable) {
                    isNicknameChanged = true;
                    checkedIcon.style.display = "inline";
                } else {
                    isNicknameChanged = false;
                    nicknameMsg.style.display = "inline";
                }
                saveButtonEnableCheck();
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
        defaultMode();
    })
}

if (cancelUserButton) {
    cancelUserButton.addEventListener('click', e =>{
        defaultMode();
    })
}

function defaultMode() {
    editUserButton.style.display = "inline";
    saveUserButton.style.display = "none";
    cancelUserButton.style.display = "none";
    terminateUserButton.style.display = "inline";

    nicknameText.style.display = "inline";
    nicknameInput.style.display = "none";
    if (nicknameInput.value !== nicknameOri.value) {
        nicknameInput.value = nicknameOri.value;
    }
    nicknameMsg.style.display = "none";
    checkedIcon.style.display = "none";
    checkNicknameButton.style.display = "none";
}

function saveButtonEnableCheck() {
    const isInputValid = isNicknameChanged; //set this const to cater for multiple validation in future
    saveUserButton.disabled = !isInputValid;
}