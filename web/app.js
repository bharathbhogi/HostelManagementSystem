function loadStudents() {
    fetch("/students")
        .then(res => res.json())
        .then(data => {
            const tableBody = document.getElementById("studentsTableBody");
            tableBody.innerHTML = ""; // clear table

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
        });
}
function searchByRoom() {
    const roomNo = document.getElementById("roomSearch").value;

    fetch(`/students?roomNo=${roomNo}`)
        .then(res => res.json())
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
        });
}


loadStudents();
