<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Reports</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
            <link href='https://cdn.jsdelivr.net/npm/@fullcalendar/core@6.1.15/index.global.min.css' rel='stylesheet' />
            <link href='https://cdn.jsdelivr.net/npm/@fullcalendar/daygrid@6.1.15/index.global.min.css' rel='stylesheet' />
            <link href='https://cdn.jsdelivr.net/npm/@fullcalendar/timegrid@6.1.15/index.global.min.css' rel='stylesheet' />
            <style>
                    #calendar {
                            max-width: 1100px;
                            margin: 40px auto;
                       
            }

                    .fc-timegrid-slot {
                            min-height: 60px !important; /* Tăng chiều cao slot */
                       
            }

                    .fc-timegrid-event {
                            margin-bottom: 6px !important; /* Khoảng cách giữa các event */
                       
            }

                    .fc-v-event .fc-event-main-frame {
                            padding: 4px;
                       
            }
                </style>
    </head>
    <body>
            <%@ include file="admin-sidebar.jsp" %>
            <div class="main-content">
                    <div class="container">
                            <div id="calendar"></div>
                        </div>
                </div>

            <!-- Modal -->
            <div class="modal fade" id="staffAssignmentModal" tabindex="-1" aria-labelledby="staffAssignmentModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                            <div class="modal-content">
                                    <div class="modal-header">
                                            <h5 class="modal-title">Assign / Edit Appointment</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                    <div class="modal-body">
                                            <input type="hidden" id="appointmentId">
                                            <div class="mb-3">
                                                    <label for="staffSelect" class="form-label">Select Staff</label>
                                                    <select class="form-select" id="staffSelect">
                                                            <c:forEach var="staff" items="${staffList}">
                                                                    <option value="${staff.id}">${staff.fullname}</option>
                                                                </c:forEach>
                                                            </select>
                                                    </div>
                                                <div class="mb-3">
                                                        <label for="appointmentDate" class="form-label">Appointment Date & Time</label>
                                                        <input type="datetime-local" id="appointmentDate" class="form-control">
                                                    </div>
                                                <div class="mb-3">
                                                        <label for="serviceSelect" class="form-label">Service</label>
                                                        <select class="form-select" id="serviceSelect">
                                                                <c:forEach var="service" items="${serviceList}">
                                                                    <option value="${service.getId()}">${service.getName()}</option>
                                                                </c:forEach>
                                                            </select>
                                                    </div>
                                            </div>
                                        <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                <button type="button" class="btn btn-primary" id="confirmAssign">Assign</button>
                                            </div>
                                    </div>
                            </div>
                    </div>

                <!-- JS -->
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/@fullcalendar/core@6.1.15/index.global.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/@fullcalendar/daygrid@6.1.15/index.global.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/@fullcalendar/timegrid@6.1.15/index.global.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/@fullcalendar/interaction@6.1.15/index.global.min.js"></script>

                <script>
                        document.addEventListener('DOMContentLoaded', function () {
                                const calendarEl = document.getElementById('calendar');
                                const calendar = new FullCalendar.Calendar(calendarEl, {
                                        initialView: 'timeGridWeek',
                                        slotMinTime: '08:00:00',
                                        slotMaxTime: '22:00:00',
                                        slotDuration: '00:15:00',
                                        slotLabelInterval: '01:00',
                                        allDaySlot: false,
                                        eventOverlap: false,
                                        slotEventOverlap: false,
                                        events: ${appointmentsJson},
                                        eventClick: function (info) {
                                                const event = info.event;
                                                $('#appointmentId').val(event.id);
                                                $('#staffSelect').val(event.extendedProps.staffId || '');
                            //                    $('#serviceSelect').val(event.extendedProps.serviceId);


                            $('#serviceSelect').val(event.extendedProps.serviceId || '');


                                                const localISO = new Date(event.start - (new Date().getTimezoneOffset() * 60000)).toISOString().slice(0, 16);
                            $('#appointmentDate').val(localISO);

                                                $('#staffAssignmentModal').modal('show');
                                        },
                                        eventColor: '#3788d8',
                                        eventDidMount: function (info) {
                                                if (!info.event.extendedProps.staffId) {
                                                        info.el.style.backgroundColor = '#ffc107';
                                                        info.el.style.borderColor = '#ffc107';
                                                } else {
                                                        info.el.style.backgroundColor = '#28a745';
                                                        info.el.style.borderColor = '#28a745';
                                                }
                                        }
                                });
                                calendar.render();

                                $('#confirmAssign').click(function () {
                                        const appointmentId = $('#appointmentId').val();
                                        const staffId = parseInt($('#staffSelect').val());
                                        const newStart = $('#appointmentDate').val();
                                        const serviceId = parseInt($('#serviceSelect').val());

                                        $.ajax({
                                                url: '/Spa/admin/workSchedule',
                                                method: 'POST',
                                                data: {
                                                        appointmentId: appointmentId,
                                                        staffId: staffId,
                                                        start: newStart,
                                                        serviceId: serviceId
                                                },
                                                success: function (response) {
                                                        if (response.success) {
                                                                const calendarEvent = calendar.getEventById(appointmentId);
                                                                if (calendarEvent) {
                                                                        const titleMap = {};
                                            <c:forEach var="staff" items="${staffList}">
                                                                            titleMap[${staff.id}] = "${staff.fullname}";
                                            </c:forEach>
                                                                        const customerName = calendarEvent.title.split(' - ')[1] || 'Unknown';
                                                                        calendarEvent.setStart(newStart);
                                                                        calendarEvent.setEnd(new Date(new Date(newStart).getTime() + 60 * 60 * 1000));
                                                                        calendarEvent.setExtendedProp('staffId', staffId);
                                                                        calendarEvent.setExtendedProp('serviceId', serviceId);
                                                                        calendarEvent.setProp('title', titleMap[staffId] + ' - ' + customerName);
                                                                        calendarEvent.setProp('backgroundColor', '#28a745');
                                                                        calendarEvent.setProp('borderColor', '#28a745');
                                                                }
                                                                $('#staffAssignmentModal').modal('hide');
                                                                alert('Appointment updated successfully.');
                                                        }
                                                }
                                        });
                                });
                        });
                </script>
    </body>
</html>
