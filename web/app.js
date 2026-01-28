const API = "http://localhost:8080";

window.onload = () => {
  loadDashboard();
  loadRooms();
  loadStudents();
};

/* ---------------- ROOMS ---------------- */

function loadRooms() {
  fetch(API + "/rooms")
    .then(r => r.json())
    .then(data => {
      let t = "<tr><th>Room</th><th>Status</th><th>Capacity</th></tr>";
      data.forEach(r =>
        t += `<tr>
          <td>${r.roomNo}</td>
          <td>${r.status}</td>
          <td>${r.capacity}</td>
        </tr>`
      );
      document.getElementById("roomsTable").innerHTML = t;
    })
    .catch(() => {
      document.getElementById("roomsTable").innerHTML =
        "<tr><td colspan='3'>Failed to load rooms</td></tr>";
    });
}

function addRoom() {
  fetch(API + "/rooms", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      roomNo: newRoomNo.value.trim(),
      capacity: Number(newCapacity.value),
      status: "AVAILABLE"
    })
  })
  .then(r => r.json())
  .then(d => {
    roomMsg.innerText = d.message;
    loadRooms();
    loadDashboard();
  });
}

/* ---------------- STUDENTS ---------------- */

function loadStudents() {
  fetch(API + "/students")
    .then(r => r.json())
    .then(data => {
      let t = "<tr><th>ID</th><th>Name</th><th>Room</th><th>Action</th></tr>";
      data.forEach(s =>
        t += `<tr>
          <td>${s.id}</td>
          <td>${s.name}</td>
          <td>${s.roomNo}</td>
          <td>
            <button onclick="removeStudent(${s.id})">Remove</button>
          </td>
        </tr>`
      );
      document.getElementById("studentsTable").innerHTML = t;
      populateStudentDropdowns(data);
    });
}

function addStudent() {
  const nameInput = document.getElementById("name");
  const phoneInput = document.getElementById("phone");
  const roomInput = document.getElementById("roomNo");

  const body = {
    name: nameInput.value.trim(),
    phone: phoneInput.value.trim(),
    roomNo: roomInput.value.trim()
  };

  if (!body.name || !body.phone || !body.roomNo) {
    document.getElementById("studentMsg").innerText = "All fields are required";
    return;
  }

  fetch(API + "/students", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body)
  })
  .then(async r => {
    const d = await r.json();
    document.getElementById("studentMsg").innerText = d.message;

    if (r.ok) {
      nameInput.value = "";
      phoneInput.value = "";
      roomInput.value = "";

      loadStudents();
      loadRooms();
      loadDashboard();
    }
  })
  .catch(() => {
    document.getElementById("studentMsg").innerText = "Server error";
  });
}

function removeStudent(id) {
  if (!confirm("Remove this student?")) return;

  fetch(API + "/students?id=" + id, { method: "DELETE" })
    .then(r => r.json())
    .then(d => {
      alert(d.message);
      loadStudents();
      loadRooms();
      loadDashboard();
    });
}

/* ---------------- FEES ---------------- */

function payFee() {
  fetch(API + "/fees", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      studentId: Number(studentSelect.value),
      amount: Number(amount.value)
    })
  })
  .then(async r => {
    const d = await r.json();
    feeMsg.innerText = d.message;
    if (r.ok) loadDashboard();
  });
}

function loadFees() {
  fetch(API + "/fees?studentId=" + feeStudentSelect.value)
    .then(r => r.json())
    .then(data => {
      let t = "<tr><th>Student</th><th>Amount</th><th>Date</th></tr>";
      data.forEach(f =>
        t += `<tr>
          <td>${f.studentId}</td>
          <td>${f.amount}</td>
          <td>${f.paidDate ?? "-"}</td>
        </tr>`
      );
      document.getElementById("feesTable").innerHTML = t;
    });
}

/* ---------------- DASHBOARD ---------------- */

function loadDashboard() {
  fetch(API + "/admin/summary")
    .then(r => r.json())
    .then(d => {
      totalRooms.innerText = d.totalRooms;
      availableRooms.innerText = d.availableRooms;
      totalStudents.innerText = d.totalStudents;
      totalFees.innerText = d.totalFeesCollected.toLocaleString("en-IN");
    });
}

/* ---------------- DROPDOWNS ---------------- */

function populateStudentDropdowns(students) {
  studentSelect.innerHTML = "";
  feeStudentSelect.innerHTML = "";

  students.forEach(s => {
    const o1 = new Option(`${s.name} (ID ${s.id})`, s.id);
    const o2 = new Option(`${s.name} (ID ${s.id})`, s.id);
    studentSelect.add(o1);
    feeStudentSelect.add(o2);
  });
}

/* ---------------- SEARCH BY ROOM ---------------- */

function searchByRoom() {
  const room = searchRoomNo.value.trim();

  if (!room) {
    alert("Enter room number");
    return;
  }

  fetch(API + "/students?roomNo=" + room)
    .then(r => r.json())
    .then(data => {
      let t = "<tr><th>ID</th><th>Name</th><th>Phone</th></tr>";

      if (data.length === 0) {
        t += "<tr><td colspan='3'>No students found</td></tr>";
      } else {
        data.forEach(s =>
          t += `<tr>
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>${s.phone}</td>
          </tr>`
        );
      }

      document.getElementById("roomStudentsTable").innerHTML = t;
    });
}
