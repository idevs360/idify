$(document).ready(function() {
	fetchIdCards();
});

function fetchIdCards() {
    $.ajax({
        type:"GET",
        url: "/idcard/list",
        success: function(res) {
            res.idCards.forEach(function(idCard) {
                let imgUrl = `/uploads/` + idCard.profilePictureName;
				console.log(imgUrl);
                $('#gallary').append(`	
				<a onclick="getIdCardById(${idCard.rollNo})" class="id-card-link">				
					    <div class="id-card" style="margin-left: 10px; margin-top: 3px;" id="id-card">
					        <div class="card-header">
					            <img src="img/logo.png" alt="Company Logo" class="logo">
					            <h2>Ginyard International School</h2>
					        </div>
					        <div class="profile-picture">
					            <img src="${imgUrl}" alt="Student Image" class="profile-img" >
					        </div>
					        <h3 id="name">${idCard.sname}</h3>
					        <p class="title"><span id="class">${idCard.sclass} ${idCard.section}</span></p>
					        <div class="details">
					            <p><strong>Roll No</strong>: <span id="rollNo">${idCard.rollNo}</span></p>
					            <p><strong>Phone</strong>: <span id="phone">+91-${idCard.phone}</span></p>
					            <p><strong>Address</strong>: <span id="address">${idCard.address}</span></p>
					        </div>
					        <div class="barcode">
					            <!-- Barcode image or actual barcode generator -->
					            <img src="img/barcode.png" alt="Barcode">
					        </div>
					    </div>		
					</a>
                `);
            });
        },
        error: function(error) {
            console.log(error);
        }
    });
}


function getIdCardById(rollNo){
	window.location.href = `/preview?rollNo=${rollNo}`;
}
