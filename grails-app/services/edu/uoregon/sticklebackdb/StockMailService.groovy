package edu.uoregon.sticklebackdb

class StockMailService {

    def mailService

    def serviceMethod() {

    }

    def sendPasswordReset(Researcher researcher,String randomPassword) {
        mailService.sendMail{
            to "${researcher.username}"
            from "ndunn@uoregon.edu"
            subject "Your password has been reset on the stickleback server."
            html "Your password has been reset on the stickleback server to '${randomPassword}'. Please change"
        }
    }
}
