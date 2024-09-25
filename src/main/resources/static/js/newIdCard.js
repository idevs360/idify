$(document).ready(function() {
});

function previewImage(e) {
    const container = document.getElementById('uploaded-image');
    const file = e.target.files[0];
    
    // Clear the container before appending a new image
    container.innerHTML = '';

    if (file) {
        const reader = new FileReader();
        
        reader.onload = function() {
            const img = document.createElement('img'); // Create a new image element
            img.src = reader.result; // Set the image source
            img.alt = file.name; // Set the alt attribute
            img.style.width = '150px'; // Set width
            img.style.height = '150px'; // Set height

            // Append the image to the container
            container.appendChild(img);
        }
        
        reader.readAsDataURL(file);
    }
}


function submitForm(e) {
    e.preventDefault();
    let form = $("#form-data")[0];  // Get the form element
    let formData = new FormData(form);  // Create FormData object

    $.ajax({
        type: "POST",
        url: "/idcard/upload",  // Adjust URL to your endpoint
        data: formData,
        contentType: false,  // Important: Set to false for FormData
        processData: false,  // Important: Prevent jQuery from processing data
        success: function(res) {
			window.location.href = "/";
            console.log(res);  // Log success message or ID
        },
        error: function(error) {
            console.log(error);
        }
    });
}
