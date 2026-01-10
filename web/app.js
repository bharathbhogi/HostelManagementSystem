document.addEventListener("DOMContentLoaded", () => {
    console.log("JS LOADED");

    fetch("/students")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("studentsTableBody");

            tableBody.innerHTML = "";

            data.forEach(student => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.roomNo}</td>
                    <td>${student.phone}</td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error fetching students:", error);
        });
});
