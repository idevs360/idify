$(document).ready(function() {
    let rollNo = getQueryParams().rollNo; // Extracting rollNo correctly
    if (rollNo) {
        getIdCardByRollNumber(rollNo);
    } else {
        alert("Roll number is missing in the URL.");
    }
});

function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        rollNo: params.get('rollNo')
    };
}

function getIdCardByRollNumber(rollNo) {
    $.ajax({
        type: "GET",
        url: `/idcard/by_roll_no/${rollNo}`,
        success: (res) => {
            let imgUrl = `/uploads/` + res.idCard.profilePictureName;
            $('#gallary').empty();
            
            $('#gallary').append(`					
                <div class="id-card">
                    <div class="card-header">
                        <img src="img/logo.png" alt="Company Logo" class="logo">
                        <h2>Ginyard International School</h2>
                    </div>
                    <div class="profile-picture">
                        <img src="${imgUrl}" alt="Student Image" class="profile-img">
                    </div>
                    <h3 id="name">${res.idCard.sname}</h3>
                    <p class="title"><span id="class">${res.idCard.sclass} ${res.idCard.section}</span></p>
                    <div class="details">
                        <p><strong>Roll No</strong>: <span id="rollNo">${res.idCard.rollNo}</span></p>
                        <p><strong>Phone</strong>: <span id="phone">+91-${res.idCard.phone}</span></p>
                        <p><strong>Address</strong>: <span id="address">${res.idCard.address}</span></p>
                    </div>
                    <div class="barcode">
                        <img src="img/barcode.png" alt="Barcode">
                    </div>
                </div>										
            `);
        },
        error: (error) => {
            alert("Something went wrong!");
            console.log("error: ", error);
        }
    });
}
